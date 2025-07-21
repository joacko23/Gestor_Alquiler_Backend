package com.joacko.gestor_alquiler.controller;

import com.joacko.gestor_alquiler.dto.AuthRequest;
import com.joacko.gestor_alquiler.dto.AuthResponse;
import com.joacko.gestor_alquiler.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthService service;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponse login(@RequestBody AuthRequest req) {
        return service.login(req);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponse register(@RequestBody AuthRequest req) {
        return service.register(req);
    }
}

