package com.joacko.gestor_alquiler.service;

import com.joacko.gestor_alquiler.dto.ReservaCreateDTO;
import com.joacko.gestor_alquiler.dto.ReservaDTO;
import com.joacko.gestor_alquiler.exception.RecursoNoEncontradoException;
import com.joacko.gestor_alquiler.mapper.ReservaMapper;
import com.joacko.gestor_alquiler.model.Alquilable;
import com.joacko.gestor_alquiler.model.Reserva;
import com.joacko.gestor_alquiler.model.Usuario;
import com.joacko.gestor_alquiler.repository.AlquilableRepository;
import com.joacko.gestor_alquiler.repository.ReservaRepository;
import com.joacko.gestor_alquiler.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservaService {

    @Autowired private ReservaRepository reservaRepo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private AlquilableRepository alquilableRepo;
    @Autowired private AlquilableService alquilableService;

    /*-------------------------------------------------
     * Crear una nueva reserva
     *------------------------------------------------*/
    public ReservaDTO crear(ReservaCreateDTO dto) {
        // Buscar usuario
        Usuario usuario = null;
        if (dto.getUsuarioEmail() != null) {
            usuario = usuarioRepo.findByEmail(dto.getUsuarioEmail())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado por email"));
        } else if (dto.getUsuarioId() != null) {
            usuario = usuarioRepo.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado por ID"));
        } else {
            throw new IllegalArgumentException("Debe enviarse el usuarioId o usuarioEmail");
        }

        // Buscar alquilable
        Alquilable alquilable = alquilableRepo.findById(dto.getAlquilableId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Alquilable no encontrado"));

        if (!alquilable.isDisponible()) {
            throw new IllegalStateException("El alquilable no está disponible");
        }

        // Preparar estrategia
        alquilable = alquilableService.prepararParaUso(alquilable);

        // Crear reserva base
        Reserva reserva = new Reserva(usuario, alquilable, dto.getInicio(), dto.getFin());

        // ✅ Calcular costo según tipo
        long cantidadTiempo;
        String tipo = alquilable.getClass().getSimpleName().toUpperCase();

        if (tipo.equals("AUTO") || tipo.equals("MOTO") || tipo.equals("CAMION")) {
            cantidadTiempo = java.time.temporal.ChronoUnit.HOURS.between(dto.getInicio(), dto.getFin());
            if (cantidadTiempo <= 0) cantidadTiempo = 1;
        } else {
            cantidadTiempo = java.time.temporal.ChronoUnit.DAYS.between(
                    dto.getInicio().toLocalDate(), dto.getFin().toLocalDate()
            );
            if (cantidadTiempo <= 0) cantidadTiempo = 1;
        }

        double costo = alquilable.getCalculadora().calcular((int) cantidadTiempo);

        // Guardar y actualizar disponibilidad
        reservaRepo.save(reserva);
        alquilable.setDisponible(false);

        // Devolver DTO con costo calculado dinámicamente
        ReservaDTO dtoResponse = ReservaMapper.toDTO(reserva);
        dtoResponse.setCostoTotal(costo);
        return dtoResponse;
    }


    /*-------------------------------------------------
     * Listar reservas
     *------------------------------------------------*/
    public List<ReservaDTO> listar() {
        return reservaRepo.findAll().stream()
                .map(reserva -> {
                    Alquilable alquilable = reserva.getAlquilable();
                    alquilableService.prepararParaUso(alquilable);
                    return ReservaMapper.toDTO(reserva);
                })
                .collect(Collectors.toList());
    }


    /*-------------------------------------------------
     * Eliminar reserva y liberar alquilable
     *------------------------------------------------*/
    public void eliminar(Long id) {
        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada"));

        Alquilable alquilable = reserva.getAlquilable();
        if (alquilable != null) {
            alquilable.setDisponible(true);
            alquilableRepo.save(alquilable);
        }

        reservaRepo.deleteById(id);
    }

    /*-------------------------------------------------
     * Actualizar reserva (fechas o alquilable)
     *------------------------------------------------*/
    public ReservaDTO actualizar(Long id, ReservaCreateDTO dto) {
        Reserva reserva = reservaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada"));

        // Cambiar alquilable si viene otro ID
        if (dto.getAlquilableId() != null &&
                !dto.getAlquilableId().equals(reserva.getAlquilable().getId())) {

            Alquilable anterior = reserva.getAlquilable();
            anterior.setDisponible(true);
            alquilableRepo.save(anterior);

            Alquilable nuevo = alquilableRepo.findById(dto.getAlquilableId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Nuevo alquilable no encontrado"));
            if (!nuevo.isDisponible()) {
                throw new IllegalStateException("El nuevo alquilable no está disponible");
            }

            nuevo.setDisponible(false);
            reserva.setAlquilable(nuevo);
        }

        // Actualizar fechas si se envían
        if (dto.getInicio() != null) reserva.setInicio(dto.getInicio());
        if (dto.getFin() != null) reserva.setFin(dto.getFin());

        // ✅ Reinyectar la estrategia antes del cálculo
        Alquilable alquilable = alquilableService.prepararParaUso(reserva.getAlquilable());

        // ✅ Calcular costo actualizado
        long cantidadTiempo;
        String tipo = alquilable.getClass().getSimpleName().toUpperCase();

        if (tipo.equals("AUTO") || tipo.equals("MOTO") || tipo.equals("CAMION")) {
            cantidadTiempo = java.time.temporal.ChronoUnit.HOURS.between(reserva.getInicio(), reserva.getFin());
            if (cantidadTiempo <= 0) cantidadTiempo = 1;
        } else {
            cantidadTiempo = java.time.temporal.ChronoUnit.DAYS.between(
                    reserva.getInicio().toLocalDate(), reserva.getFin().toLocalDate()
            );
            if (cantidadTiempo <= 0) cantidadTiempo = 1;
        }

        double costo = alquilable.getCalculadora().calcular((int) cantidadTiempo);

        reservaRepo.save(reserva);

        // Crear DTO con el costo recalculado
        ReservaDTO dtoResponse = ReservaMapper.toDTO(reserva);
        dtoResponse.setCostoTotal(costo);

        return dtoResponse;
    }


    /*-------------------------------------------------
     * Listar reservas por usuario
     *------------------------------------------------*/
    public List<ReservaDTO> listarPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        return reservaRepo.findAll().stream()
                .filter(r -> r.getUsuario().equals(usuario))
                .peek(r -> {
                    // ✅ Reinyectar la estrategia antes de convertir a DTO
                    if (r.getAlquilable() != null) {
                        alquilableService.prepararParaUso(r.getAlquilable());
                    }
                })
                .map(ReservaMapper::toDTO)
                .collect(Collectors.toList());
    }


}
