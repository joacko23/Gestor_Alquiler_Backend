package com.joacko.gestor_alquiler.controller;

import com.joacko.gestor_alquiler.dto.AlquilableDTO;
import com.joacko.gestor_alquiler.factory.TipoAlquilable;
import com.joacko.gestor_alquiler.service.AlquilableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alquilables")
public class AlquilableController {

    @Autowired
    private AlquilableService service;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AlquilableDTO obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlquilableDTO> listar() {
        return service.listarTodos();
    }

    @PostMapping(value = "/crear", produces = MediaType.APPLICATION_JSON_VALUE)
    public AlquilableDTO crear(
            @RequestParam TipoAlquilable tipo,
            @RequestParam String marca) {
        return service.crear(tipo, marca);
    }

    @PatchMapping(value = "/{id}/disponibilidad",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AlquilableDTO cambiarDisponibilidad(
            @PathVariable Long id,
            @RequestParam boolean disponible) {
        return service.cambiarDisponibilidad(id, disponible);
    }
}


