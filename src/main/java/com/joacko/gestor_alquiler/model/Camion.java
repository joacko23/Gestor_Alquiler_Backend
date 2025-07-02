package com.joacko.gestor_alquiler.model;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Camion extends Alquilable{

    public Camion(String marca) {
        super(marca);
    }

    @Override
    public double calcularCosto(int horas) {
        return horas * 150;
    }
}
