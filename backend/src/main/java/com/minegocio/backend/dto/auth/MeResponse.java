package com.minegocio.backend.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeResponse {
    private String correo;
    private String nombre;
    private boolean emailVerificado;
}
