package com.joacko.gestor_alquiler.model;

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

    @Transient // No se persiste en la base de datos, va a estar null y hay que reasignarla
    protected EstrategiaCosto estrategiaCosto;

    public Alquilable(String marca, EstrategiaCosto estrategiaCosto) {
        this.marca = marca;
        this.estrategiaCosto = estrategiaCosto;
    }

    public double calcularCosto(int cantidadTiempo) {
        if (estrategiaCosto == null) throw new IllegalStateException("Estrategia de costo no definida");
        return estrategiaCosto.calcularCosto(cantidadTiempo);
    }
}
