package com.joacko.gestor_alquiler.dto;

import com.joacko.gestor_alquiler.factory.TipoAlquilable;
import lombok.Data;

@Data
public class AlquilableDTO {
    private Long id;
    private TipoAlquilable tipo;
    private String marca;
    private boolean disponible;
}

