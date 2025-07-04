package com.joacko.gestor_alquiler.factory;

import com.joacko.gestor_alquiler.model.Alquilable;
import com.joacko.gestor_alquiler.model.*;
import org.springframework.stereotype.Component;

@Component
public class AlquilableFactoryImpl implements AlquilableFactory{
    @Override
    public Alquilable crear(TipoAlquilable tipo, String marca) {
        return switch (tipo) {
            case AUTO -> new Auto(marca);
            case MOTO -> new Moto(marca);
            case CAMION -> new Camion(marca);
            case ELECTRODOMESTICO -> new Electrodomestico(marca);
        };
    }
}
