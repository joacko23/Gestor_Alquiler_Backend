package com.joacko.gestor_alquiler.model;

import com.joacko.gestor_alquiler.strategy.CostoPorDia;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Electrodomestico extends Alquilable {

    public Electrodomestico(String marca) {
        super(marca, new CostoPorDia(150)); // 150 por día
    }
}

