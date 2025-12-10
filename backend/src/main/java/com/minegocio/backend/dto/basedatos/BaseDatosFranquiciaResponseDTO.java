package com.minegocio.backend.dto.basedatos;

import com.minegocio.backend.model.enums.TipoBaseDatos;
import com.minegocio.backend.model.enums.EstadoBaseDatos;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseDatosFranquiciaResponseDTO {

    private Integer id;
    private Integer franquiciaId;
    private String nombreBd;
    private TipoBaseDatos tipoBd;
    private EstadoBaseDatos estado;

    private String host;
    private Integer port;
    private String driver;
    private String usuarioConexion;
    private String urlConexion;
    private String passConexion;

    private Integer createdBy;
    private Integer updatedBy;

    private Timestamp createdAt;
    private Timestamp updatedAt;
}
