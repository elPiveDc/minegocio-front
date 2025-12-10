package com.minegocio.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

import com.minegocio.backend.model.enums.TipoTemplate;

@Entity
@Table(name = "franchise_templates", indexes = {
        @Index(name = "idx_tpl_frn", columnList = "id_franquicia")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranchiseTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_template")
    private Integer id;

    // Relación con franquicia (nullable, SET NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_franquicia", foreignKey = @ForeignKey(name = "fk_tpl_frn"))
    private Franquicia franquicia;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('BLOQUES_JSON','HTML_LIMITED')")
    private TipoTemplate tipo = TipoTemplate.BLOQUES_JSON;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] contenido;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

}
