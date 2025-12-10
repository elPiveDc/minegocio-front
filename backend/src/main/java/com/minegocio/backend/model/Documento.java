package com.minegocio.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "documento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(nullable = false, length = 150, unique = true)
    private String slug;

    @Column(name = "tipo_contenido", nullable = false, length = 100)
    private String tipoContenido;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] archivo;

    @Column(name = "fecha_subida", insertable = false, updatable = false)
    private Timestamp fechaSubida;
}
