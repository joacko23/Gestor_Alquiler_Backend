package com.joacko.gestor_alquiler.factory;

import com.joacko.gestor_alquiler.model.Alquilable;
import com.joacko.gestor_alquiler.model.Auto;

public class AutoCreator extends AlquilableCreator {
    @Override
    public Alquilable crear(String marca) {
        return new Auto(marca);
    }
}
