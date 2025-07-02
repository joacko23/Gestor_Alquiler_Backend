package com.joacko.gestor_alquiler.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Alquilable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;

    private boolean disponible = true;

    public Alquilable(String marca) {
        this.marca = marca;
    }

    public abstract double calcularCosto(int horas);
}