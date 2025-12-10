package com.minegocio.backend.dto.objetobdfranquicia;

import com.minegocio.backend.model.enums.TipoObjeto;
import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Builder
public class ObjetoBdFranquiciaResponse {

    private Integer id;
    private Integer idBaseDatos;
    private Integer idModulo;
    private String nombreTabla;
    private TipoObjeto tipoObjeto;
    private Boolean esTablaUsuarios;
    private String columnas;
    private Boolean editable;
    private Timestamp fechaCreacion;
    private Integer createdBy;
    private Integer updatedBy;
    private Timestamp updatedAt;
}