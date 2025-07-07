package com.joacko.gestor_alquiler.service;

import com.joacko.gestor_alquiler.dto.AlquilableDTO;
import com.joacko.gestor_alquiler.dto.AlquilableUpdateDTO;
import com.joacko.gestor_alquiler.exception.RecursoNoEncontradoException;
import com.joacko.gestor_alquiler.factory.AlquilableFactory;
import com.joacko.gestor_alquiler.factory.TipoAlquilable;
import com.joacko.gestor_alquiler.mapper.AlquilableMapper;
import com.joacko.gestor_alquiler.model.Alquilable;
import com.joacko.gestor_alquiler.repository.AlquilableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlquilableService {

    @Autowired
    private AlquilableRepository repository;

    @Autowired
    private AlquilableFactory factory;

    /*-------------------------------------------------
     * Crear un nuevo Alquilable
     *------------------------------------------------*/
    public AlquilableDTO crear(TipoAlquilable tipo, String marca) {
        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException("La marca no puede estar vacía");
        }
        Alquilable entidad = factory.crear(tipo, marca);
        return AlquilableMapper.toDTO(repository.save(entidad));
    }

    /*-------------------------------------------------
     * Listar todos
     *------------------------------------------------*/
    public List<AlquilableDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::reinyectarEstrategia)      // evita estrategias nulas
                .map(AlquilableMapper::toDTO)
                .collect(Collectors.toList());
    }

    /*-------------------------------------------------
     * Obtener uno por ID
     *------------------------------------------------*/
    public AlquilableDTO obtenerPorId(Long id) {
        Alquilable entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Alquilable con ID " + id + " no encontrado"));
        return AlquilableMapper.toDTO(reinyectarEstrategia(entity));
    }

    /*-------------------------------------------------
     * Cambiar disponibilidad
     *------------------------------------------------*/
    public AlquilableDTO cambiarDisponibilidad(Long id, boolean disponible) {
        Alquilable entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Alquilable con ID " + id + " no encontrado"));
        entity.setDisponible(disponible);
        return AlquilableMapper.toDTO(entity);
    }

    /*-------------------------------------------------
     * Utilitario: vuelve a colocar la estrategia
     *------------------------------------------------*/
    private Alquilable reinyectarEstrategia(Alquilable entity) {
        if (entity.getEstrategiaCosto() == null) {
            TipoAlquilable tipo = TipoAlquilable.valueOf(
                    entity.getClass().getSimpleName().toUpperCase());
            entity.setEstrategiaCosto(
                    factory.crear(tipo, entity.getMarca()).getEstrategiaCosto());
        }
        return entity;
    }

    /*-------------------------------------------------
     * Buscar con filtros
     *------------------------------------------------*/
    public List<AlquilableDTO> buscar(TipoAlquilable tipo, String marca, Boolean disponible) {
        return repository.findAll().stream()
                .filter(a -> tipo == null || TipoAlquilable.valueOf(a.getClass().getSimpleName().toUpperCase()) == tipo)
                .filter(a -> marca == null || a.getMarca().equalsIgnoreCase(marca))
                .filter(a -> disponible == null || a.isDisponible() == disponible)
                .map(this::reinyectarEstrategia)
                .map(AlquilableMapper::toDTO)
                .collect(Collectors.toList());
    }

    /*-------------------------------------------------
     * Estimar costos
     *------------------------------------------------*/
    public double estimarCosto(Long id, int dias) {
        if (dias <= 0) throw new IllegalArgumentException("La cantidad de días debe ser mayor a 0");
        Alquilable entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("ID no encontrado"));
        return reinyectarEstrategia(entity).calcularCosto(dias);
    }

    /*-------------------------------------------------
     * Actualizar
     *------------------------------------------------*/
    public AlquilableDTO actualizar(Long id, AlquilableUpdateDTO dto) {
        Alquilable entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("ID no encontrado"));

        if (dto.getMarca() != null && !dto.getMarca().isBlank()) {
            entity.setMarca(dto.getMarca());
        }

        return AlquilableMapper.toDTO(repository.save(entity));
    }

    /*-------------------------------------------------
     * Actualizar
     *------------------------------------------------*/
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró el recurso a eliminar");
        }
        repository.deleteById(id);
    }


}


