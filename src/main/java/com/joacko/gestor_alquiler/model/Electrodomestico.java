package com.joacko.gestor_alquiler.model;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

//Clase para extensibilidad futura
@Entity
@NoArgsConstructor
public class Electrodomestico extends Alquilable {

    public Electrodomestico(String marca) {
        super(marca);
    }

    @Override
    public double calcularCosto(int horas) {
        return horas * 50;
    }
}
