package com.minegocio.backend.dto.consulta;

import com.minegocio.backend.model.enums.TipoConsulta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConsultaUpdateDto {

    @NotNull
    private TipoConsulta tipoConsulta;

    @NotBlank
    private String descripcion;
}
