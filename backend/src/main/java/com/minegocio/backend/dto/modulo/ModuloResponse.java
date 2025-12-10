package com.minegocio.backend.dto.modulo;

import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Builder
public class ModuloResponse {

    private Integer id;
    private String clave;
    private String nombre;
    private String descripcion;
    private Timestamp createdAt;
}