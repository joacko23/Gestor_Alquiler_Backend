package com.joacko.gestor_alquiler.model;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Moto extends Alquilable {

    public Moto(String marca) {
        super(marca);
    }

    @Override
    public double calcularCosto(int horas) {
        return horas * 60;
    }
}
