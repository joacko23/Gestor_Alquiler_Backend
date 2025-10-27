package com.joacko.gestor_alquiler.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    private Alquilable alquilable;

    private LocalDateTime inicio;
    private LocalDateTime fin;

    public Reserva(Usuario usuario, Alquilable alquilable, LocalDateTime inicio, LocalDateTime fin) {
        this.usuario = usuario;
        this.alquilable = alquilable;
        this.inicio = inicio;
        this.fin = fin;
    }
}

