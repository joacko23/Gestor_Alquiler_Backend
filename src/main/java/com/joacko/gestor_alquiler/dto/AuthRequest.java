package com.joacko.gestor_alquiler.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}

