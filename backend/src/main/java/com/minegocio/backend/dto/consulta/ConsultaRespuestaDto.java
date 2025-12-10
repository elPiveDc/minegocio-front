package com.minegocio.backend.dto.consulta;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConsultaRespuestaDto {
    @NotBlank
    private String respuesta;
}
