package com.minegocio.backend.dto.consulta;

import com.minegocio.backend.model.enums.EstadoConsulta;
import com.minegocio.backend.model.enums.TipoConsulta;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ConsultaResponseDto {

    private Integer id;
    private Integer idUsuario;

    private TipoConsulta tipoConsulta;
    private String descripcion;

    private String respuesta;

    private Timestamp fechaCreacion;

    private EstadoConsulta estado;
}
