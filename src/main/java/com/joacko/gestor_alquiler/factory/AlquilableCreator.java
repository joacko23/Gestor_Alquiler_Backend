package com.joacko.gestor_alquiler.factory;

import com.joacko.gestor_alquiler.model.Alquilable;

/**
 * Clase base del patrón Factory Method.
 * Define el método fábrica (factory method) que será implementado por subclases concretas.
 */
public abstract class AlquilableCreator {

    public abstract Alquilable crear(String marca);

    // Método común para extender comportamiento si querés (opcional)
    public Alquilable registrar(String marca) {
        Alquilable alquilable = crear(marca);
        System.out.println("Registrado nuevo alquilable: " + alquilable.getClass().getSimpleName());
        return alquilable;
    }
}

