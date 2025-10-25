package com.joacko.gestor_alquiler.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaCreateDTO {
    private Long usuarioId;
    private Long alquilableId;
    private LocalDateTime inicio;
    private LocalDateTime fin;
}
