package com.minegocio.backend.model;

import com.minegocio.backend.model.enums.RolUsuario;
import com.minegocio.backend.model.enums.UsuarioEstado;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 255)
    private String correo;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String password;

    @Column(name = "email_verificado", nullable = false)
    private Boolean emailVerificado = false;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private Timestamp fechaRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private RolUsuario Rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UsuarioEstado estado = UsuarioEstado.ACTIVO;

    @Column(name = "avatar_url", length = 512)
    private String avatarUrl;

    @Column(name = "zona_horaria", length = 64)
    private String zonaHoraria;

    @Column(name = "last_login")
    private Timestamp lastLogin;

}
