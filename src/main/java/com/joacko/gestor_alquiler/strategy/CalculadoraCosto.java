package com.joacko.gestor_alquiler.strategy;

public class CalculadoraCosto {
    private EstrategiaCosto estrategia;

    public EstrategiaCosto getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(EstrategiaCosto estrategia) {
        this.estrategia = estrategia;
    }

    public double calcular(int cantidadTiempo) {
        if (estrategia == null) throw new IllegalStateException("Estrategia no definida");
        return estrategia.calcularCosto(cantidadTiempo);
    }
}
