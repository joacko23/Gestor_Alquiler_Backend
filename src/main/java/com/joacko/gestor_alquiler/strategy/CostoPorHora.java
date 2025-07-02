package com.joacko.gestor_alquiler.strategy;

public class CostoPorHora implements EstrategiaCosto{

    private final double precioPorHora;

    public CostoPorHora(double precioPorHora) {
        this.precioPorHora = precioPorHora;
    }

    @Override
    public double calcularCosto(int horas) {
        return horas * precioPorHora;
    }
}
