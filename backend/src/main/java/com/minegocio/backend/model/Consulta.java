package com.minegocio.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

import com.minegocio.backend.model.enums.EstadoConsulta;
import com.minegocio.backend.model.enums.TipoConsulta;

@Entity
@Table(name = "consultas", indexes = {
        @Index(name = "idx_cons_usuario", columnList = "id_usuario"),
        @Index(name = "idx_cons_estado", columnList = "estado")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_cons_usuario"))
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_consulta", nullable = false, columnDefinition = "ENUM('consulta','actualizacion','error','otro')")
    private TipoConsulta tipoConsulta;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String respuesta;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private Timestamp fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('PENDIENTE','EN_PROCESO','RESUELTO','CERRADO')")
    private EstadoConsulta estado = EstadoConsulta.PENDIENTE;

}
