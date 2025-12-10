package com.minegocio.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

import com.minegocio.backend.model.enums.TipoObjeto;

@Entity
@Table(name = "objetos_bd_franquicia", indexes = {
        @Index(name = "idx_obj_bd", columnList = "id_bd"),
        @Index(name = "idx_obj_tabla", columnList = "nombre_tabla")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjetoBdFranquicia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_objeto")
    private Integer id;

    // RELACIÓN CON bases_datos_franquicia
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bd", nullable = false, foreignKey = @ForeignKey(name = "fk_obj_bd"))
    private BaseDatosFranquicia baseDatos;

    @Column(name = "nombre_tabla", length = 150, nullable = false)
    private String nombreTabla;

    // ENUM tipo_objeto
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_objeto", nullable = false, columnDefinition = "ENUM('TABLA','VISTA','FUNCION','OTRO')")
    private TipoObjeto tipoObjeto = TipoObjeto.TABLA;

    @Column(name = "es_tabla_usuarios", nullable = false)
    private Boolean esTablaUsuarios = false;

    @Column(columnDefinition = "JSON", nullable = false)
    private String columnas;

    // RELACIÓN CON modulo (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modulo", foreignKey = @ForeignKey(name = "fk_obj_mod"))
    private Modulo modulo;

    @Column(nullable = false)
    private Boolean editable = true;

    @Column(name = "fecha_creacion", updatable = false, insertable = false)
    private Timestamp fechaCreacion;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at", insertable = false)
    private Timestamp updatedAt;

}
