package com.joacko.gestor_alquiler.strategy;

public class CostoPorDia implements EstrategiaCosto {

    private final double precioPorDia;

    public CostoPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
    }

    @Override
    public double calcularCosto(int dias) {
        return dias * precioPorDia;
    }
}
