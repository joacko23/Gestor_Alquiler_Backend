package com.joacko.gestor_alquiler.factory;

import com.joacko.gestor_alquiler.model.Alquilable;
import com.joacko.gestor_alquiler.model.Moto;

public class MotoCreator extends AlquilableCreator {
    @Override
    public Alquilable crear(String marca) {
        return new Moto(marca);
    }
}

