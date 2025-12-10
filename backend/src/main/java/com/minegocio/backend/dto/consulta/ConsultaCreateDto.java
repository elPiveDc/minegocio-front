package com.minegocio.backend.dto.consulta;

import com.minegocio.backend.model.enums.TipoConsulta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConsultaCreateDto {

    @NotNull
    private Integer idUsuario;

    @NotNull
    private TipoConsulta tipoConsulta;

    @NotBlank
    private String descripcion;
}
