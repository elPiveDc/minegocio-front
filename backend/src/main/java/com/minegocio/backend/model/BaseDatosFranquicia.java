package com.minegocio.backend.model;

import com.minegocio.backend.model.enums.TipoBaseDatos;
import com.minegocio.backend.model.enums.EstadoBaseDatos;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "bases_datos_franquicia", indexes = {
        @Index(name = "idx_bd_franquicia", columnList = "id_franquicia"),
        @Index(name = "idx_bd_nombre", columnList = "nombre_bd")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseDatosFranquicia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bd")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_franquicia", nullable = false, foreignKey = @ForeignKey(name = "fk_bd_frn"))
    private Franquicia franquicia;

    @Column(name = "nombre_bd", nullable = false, length = 150)
    private String nombreBd;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_bd", length = 20, nullable = false)
    @Builder.Default
    private TipoBaseDatos tipoBd = TipoBaseDatos.MYSQL;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20, nullable = false)
    @Builder.Default
    private EstadoBaseDatos estado = EstadoBaseDatos.NO_CONFIGURADA;

    @Column(length = 255)
    private String host;

    private Integer port;

    @Column(length = 100)
    private String driver;

    @Column(name = "usuario_conexion", length = 150)
    private String usuarioConexion;

    @Column(name = "pass_conexion_encrypted", length = 512)
    private String passConexionEncrypted;

    @Column(name = "url_conexion", columnDefinition = "TEXT")
    private String urlConexion;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
