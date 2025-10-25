package com.joacko.gestor_alquiler.controller;

import com.joacko.gestor_alquiler.dto.ReservaCreateDTO;
import com.joacko.gestor_alquiler.dto.ReservaDTO;
import com.joacko.gestor_alquiler.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired private ReservaService service;

    /*-------------------------------------------------
     * Crear reserva
     *------------------------------------------------*/
    @PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservaDTO crear(@RequestBody ReservaCreateDTO dto) {
        return service.crear(dto);
    }

    /*-------------------------------------------------
     * Listar todas
     *------------------------------------------------*/
    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservaDTO> listar() {
        return service.listar();
    }

    /*-------------------------------------------------
     * Eliminar reserva
     *------------------------------------------------*/
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    /*-------------------------------------------------
     * Actualizar reserva
     *------------------------------------------------*/
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservaDTO actualizar(@PathVariable Long id, @RequestBody ReservaCreateDTO dto) {
        return service.actualizar(id, dto);
    }

    /*-------------------------------------------------
     * Listar por usuario
     *------------------------------------------------*/
    @GetMapping(value = "/usuario/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservaDTO> listarPorUsuario(@PathVariable Long id) {
        return service.listarPorUsuario(id);
    }

}
