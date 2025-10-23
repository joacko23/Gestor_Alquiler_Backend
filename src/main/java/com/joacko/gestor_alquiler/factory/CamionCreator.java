package com.joacko.gestor_alquiler.factory;

import com.joacko.gestor_alquiler.model.Alquilable;
import com.joacko.gestor_alquiler.model.Camion;

public class CamionCreator extends AlquilableCreator {
    @Override
    public Alquilable crear(String marca) {
        return new Camion(marca);
    }
}
