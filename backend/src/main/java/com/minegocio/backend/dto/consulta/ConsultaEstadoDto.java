package com.minegocio.backend.dto.consulta;

import com.minegocio.backend.model.enums.EstadoConsulta;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConsultaEstadoDto {
    @NotNull
    private EstadoConsulta estado;
}
