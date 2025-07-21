package com.joacko.gestor_alquiler.service;

import com.joacko.gestor_alquiler.dto.AuthRequest;
import com.joacko.gestor_alquiler.dto.AuthResponse;
import com.joacko.gestor_alquiler.exception.BadRequestException;
import com.joacko.gestor_alquiler.model.Usuario;
import com.joacko.gestor_alquiler.repository.UsuarioRepository;
import com.joacko.gestor_alquiler.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private UsuarioRepository repo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtil jwt;
    @Autowired private AuthenticationManager authManager;

    public AuthResponse login(AuthRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authManager.authenticate(authToken); // lanza excepción si falla

        return new AuthResponse(jwt.generateToken(request.getEmail()));
    }

    public AuthResponse register(AuthRequest request) {
        if (repo.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Ya existe un usuario con ese email");
        }

        Usuario u = new Usuario();
        u.setEmail(request.getEmail());
        u.setPassword(encoder.encode(request.getPassword()));
        u.setNombre("Usuario Nuevo");
        u.setRol("USER");

        repo.save(u);

        return new AuthResponse(jwt.generateToken(u.getEmail()));
    }
}
