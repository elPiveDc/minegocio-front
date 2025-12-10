package com.minegocio.backend.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {
    private String correo;
    private String mensaje;
}
