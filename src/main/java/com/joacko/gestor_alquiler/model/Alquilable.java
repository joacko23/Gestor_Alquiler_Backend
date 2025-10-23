package com.joacko.gestor_alquiler.model;

import com.joacko.gestor_alquiler.strategy.CalculadoraCosto;
import com.joacko.gestor_alquiler.strategy.EstrategiaCosto;
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

    @Transient
    protected CalculadoraCosto calculadora = new CalculadoraCosto();

    public Alquilable(String marca, EstrategiaCosto estrategiaCosto) {
        this.marca = marca;
        this.calculadora.setEstrategia(estrategiaCosto);
    }

    public double calcularCosto(int cantidadTiempo) {
        return calculadora.calcular(cantidadTiempo);
    }

}
