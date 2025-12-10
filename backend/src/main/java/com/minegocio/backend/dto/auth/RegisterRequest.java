package com.minegocio.backend.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String nombre;
    private String correo;
    private String password;
}
