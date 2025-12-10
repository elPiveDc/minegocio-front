package com.minegocio.backend.dto.franquiciausuario;

import com.minegocio.backend.model.enums.FranquiciaUsuarioRol;
import com.minegocio.backend.model.enums.FranquiciaUsuarioEstado;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class FranquiciaUsuarioResponse {

    private Integer id;
    private Integer idFranquicia;
    private Integer idUsuario;
    private FranquiciaUsuarioRol rol;
    private FranquiciaUsuarioEstado estado;
    private Timestamp fechaAsignacion;
}