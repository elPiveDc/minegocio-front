package com.minegocio.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.minegocio.backend.model.enums.EstadoFranquicia;

@Entity
@Table(name = "franquicias", indexes = {
        @Index(name = "idx_franquicias_slug", columnList = "slug"),
        @Index(name = "idx_frn_nombre", columnList = "nombre_franquicia")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Franquicia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_franquicia")
    private Integer id;

    @Column(name = "nombre_franquicia", length = 150, nullable = false)
    private String nombreFranquicia;

    @Column(name = "slug", length = 160, nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    @Builder.Default
    private EstadoFranquicia estado = EstadoFranquicia.ACTIVA;

    @Column(name = "template_id")
    private Integer templateId;

}
