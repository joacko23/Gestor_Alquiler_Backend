package com.joacko.gestor_alquiler.model;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Auto extends Alquilable {

    public Auto(String marca) {
        super(marca);
    }

    @Override
    public double calcularCosto(int horas) {
        return horas * 100;
    }
}
