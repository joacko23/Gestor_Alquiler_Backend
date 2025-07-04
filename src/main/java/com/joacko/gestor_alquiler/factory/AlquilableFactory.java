package com.joacko.gestor_alquiler.factory;

import com.joacko.gestor_alquiler.model.Alquilable;


public interface AlquilableFactory {
    Alquilable crear(TipoAlquilable tipo, String marca);
}
