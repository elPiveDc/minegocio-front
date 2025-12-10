package com.minegocio.backend.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;
    private String correo;
    private String nombre;
}
