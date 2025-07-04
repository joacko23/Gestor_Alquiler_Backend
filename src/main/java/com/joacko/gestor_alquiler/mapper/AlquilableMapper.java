package com.joacko.gestor_alquiler.mapper;

import com.joacko.gestor_alquiler.dto.AlquilableDTO;
import com.joacko.gestor_alquiler.factory.TipoAlquilable;
import com.joacko.gestor_alquiler.model.Alquilable;
import org.springframework.stereotype.Component;

@Component
public class AlquilableMapper {
    public static AlquilableDTO toDTO(Alquilable entity) {
        AlquilableDTO dto = new AlquilableDTO();
        dto.setId(entity.getId());
        dto.setMarca(entity.getMarca());
        dto.setDisponible(entity.isDisponible());
        dto.setTipo(TipoAlquilable.valueOf(entity.getClass().getSimpleName().toUpperCase()));
        return dto;
    }
}
