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

    /*-------------------------------------------------
     * Crear una nueva reserva
     *------------------------------------------------*/
    public ReservaDTO crear(ReservaCreateDTO dto) {
        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        Alquilable alquilable = alquilableRepo.findById(dto.getAlquilableId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Alquilable no encontrado"));

        if (!alquilable.isDisponible()) {
            throw new IllegalStateException("El alquilable no está disponible");
        }

        Reserva reserva = new Reserva(usuario, alquilable, dto.getInicio(), dto.getFin());
        reservaRepo.save(reserva);

        // 🔒 Marcar alquilable como no disponible
        alquilable.setDisponible(false);

        return ReservaMapper.toDTO(reserva);
    }

    /*-------------------------------------------------
     * Listar reservas
     *------------------------------------------------*/
    public List<ReservaDTO> listar() {
        return reservaRepo.findAll().stream()
                .map(ReservaMapper::toDTO)
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

        // Si cambia el alquilable, liberar el anterior y asignar el nuevo
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

        reservaRepo.save(reserva);
        return ReservaMapper.toDTO(reserva);
    }

    /*-------------------------------------------------
     * Listar reservas por usuario
     *------------------------------------------------*/
    public List<ReservaDTO> listarPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        return reservaRepo.findAll().stream()
                .filter(r -> r.getUsuario().equals(usuario))
                .map(ReservaMapper::toDTO)
                .collect(Collectors.toList());
    }

}
