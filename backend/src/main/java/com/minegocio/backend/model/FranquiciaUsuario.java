package com.minegocio.backend.model;

import com.minegocio.backend.model.enums.FranquiciaUsuarioRol;
import com.minegocio.backend.model.enums.FranquiciaUsuarioEstado;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "franquicia_usuarios", uniqueConstraints = {
                @UniqueConstraint(name = "ux_franquicia_usuario", columnNames = { "id_franquicia", "id_usuario" })
}, indexes = {
                @Index(name = "idx_fu_frn", columnList = "id_franquicia"),
                @Index(name = "idx_fu_usr", columnList = "id_usuario")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranquiciaUsuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_franquicia", nullable = false, foreignKey = @ForeignKey(name = "fk_fu_franquicia"))
        private Franquicia franquicia;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_fu_usuario"))
        private Usuario usuario;

        @Enumerated(EnumType.STRING)
        @Column(name = "rol", length = 20, nullable = false)
        @Builder.Default
        private FranquiciaUsuarioRol rol = FranquiciaUsuarioRol.INVITED;

        @Enumerated(EnumType.STRING)
        @Column(name = "estado", length = 20, nullable = false)
        @Builder.Default
        private FranquiciaUsuarioEstado estado = FranquiciaUsuarioEstado.PENDIENTE;

        @Column(name = "fecha_asignacion")
        private Timestamp fechaAsignacion;
}
