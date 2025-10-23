package com.joacko.gestor_alquiler.factory;

import com.joacko.gestor_alquiler.model.Alquilable;
import com.joacko.gestor_alquiler.model.Electrodomestico;

public class ElectrodomesticoCreator extends AlquilableCreator {
    @Override
    public Alquilable crear(String marca) {
        return new Electrodomestico(marca);
    }
}
