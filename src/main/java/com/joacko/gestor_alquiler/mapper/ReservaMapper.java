package com.joacko.gestor_alquiler.mapper;

import com.joacko.gestor_alquiler.dto.ReservaDTO;
import com.joacko.gestor_alquiler.model.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public static ReservaDTO toDTO(Reserva entity) {
        ReservaDTO dto = new ReservaDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuario().getId());
        dto.setUsuarioEmail(entity.getUsuario().getEmail());
        dto.setAlquilableId(entity.getAlquilable().getId());
        dto.setAlquilableMarca(entity.getAlquilable().getMarca());
        dto.setInicio(entity.getInicio());
        dto.setFin(entity.getFin());
        // El costo viene calculado desde el Service
        dto.setCostoTotal(0.0);
        return dto;
    }
}
