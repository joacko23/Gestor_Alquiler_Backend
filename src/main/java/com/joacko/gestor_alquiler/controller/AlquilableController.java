package com.joacko.gestor_alquiler.controller;

import com.joacko.gestor_alquiler.dto.AlquilableDTO;
import com.joacko.gestor_alquiler.dto.AlquilableUpdateDTO;
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

    // Crear nuevo
    @PostMapping(value = "/crear", produces = MediaType.APPLICATION_JSON_VALUE)
    public AlquilableDTO crear(
            @RequestParam TipoAlquilable tipo,
            @RequestParam String marca) {
        return service.crear(tipo, marca);
    }


    //Listar todos
    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlquilableDTO> listar() {
        return service.listarTodos();
    }

    // Obtener por ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AlquilableDTO obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    //Cambiar disponibilidad
    @PatchMapping(value = "/{id}/disponibilidad",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AlquilableDTO cambiarDisponibilidad(
            @PathVariable Long id,
            @RequestParam boolean disponible) {
        return service.cambiarDisponibilidad(id, disponible);
    }

    //Buscar con filtros
    @GetMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlquilableDTO> buscar(
            @RequestParam(required = false) TipoAlquilable tipo,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Boolean disponible) {
        return service.buscar(tipo, marca, disponible);
    }

    //Estimar costos
    @GetMapping(value = "/{id}/costo", produces = MediaType.APPLICATION_JSON_VALUE)
    public double estimarCosto(@PathVariable Long id, @RequestParam int dias) {
        return service.estimarCosto(id, dias);
    }

    //Actualizar
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AlquilableDTO actualizar(@PathVariable Long id, @RequestBody AlquilableUpdateDTO dto) {
        return service.actualizar(id, dto);
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

}


