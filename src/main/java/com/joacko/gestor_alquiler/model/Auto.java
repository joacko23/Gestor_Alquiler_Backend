package com.joacko.gestor_alquiler.model;

import com.joacko.gestor_alquiler.strategy.CostoPorHora;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Auto extends Alquilable {

    public Auto(String marca) {
        super(marca, new CostoPorHora(120)); // 120 por hora
    }
}

