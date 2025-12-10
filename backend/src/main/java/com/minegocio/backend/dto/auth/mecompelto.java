package com.minegocio.backend.dto.auth;

import java.sql.Timestamp;

import com.minegocio.backend.model.enums.UsuarioEstado;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class mecompelto {

    private String nombre;

    private String correo;

    private Boolean emailVerificado;

    private Timestamp fechaRegistro;

    private UsuarioEstado estado;

    private String avatarUrl;

    private String zonaHoraria;

    private Timestamp lastLogin;

}
