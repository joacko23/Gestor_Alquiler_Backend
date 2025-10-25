package com.joacko.gestor_alquiler.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioEmail;
    private Long alquilableId;
    private String alquilableMarca;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private double costoTotal;
}
