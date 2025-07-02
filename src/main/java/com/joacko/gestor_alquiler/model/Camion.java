package com.joacko.gestor_alquiler.model;

import com.joacko.gestor_alquiler.strategy.CostoPorHora;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Camion extends Alquilable {

    public Camion(String marca) {
        super(marca, new CostoPorHora(200));
    }
}

