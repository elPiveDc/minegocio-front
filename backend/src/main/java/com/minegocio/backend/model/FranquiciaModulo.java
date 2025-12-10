package com.minegocio.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "franquicia_modulos", uniqueConstraints = {
                @UniqueConstraint(name = "ux_fm_frn_mod", columnNames = { "id_franquicia", "id_modulo" })
}, indexes = {
                @Index(name = "idx_fm_frn", columnList = "id_franquicia")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranquiciaModulo {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        // RELACIÓN MANY-TO-ONE → FRANQUICIA
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_franquicia", nullable = false, foreignKey = @ForeignKey(name = "fk_fm_frn"))
        private Franquicia franquicia;

        // RELACIÓN MANY-TO-ONE → MODULO
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_modulo", nullable = false, foreignKey = @ForeignKey(name = "fk_fm_mod"))
        private Modulo modulo;

        @Column(nullable = false)
        private Boolean activo = true;

        // Usamos TEXT para mapear JSON
        @Column(columnDefinition = "JSON")
        private String configuracion;

        @Column(name = "fecha_instalacion", updatable = false, insertable = false)
        private Timestamp fechaInstalacion;
}
