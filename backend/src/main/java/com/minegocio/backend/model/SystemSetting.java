package com.minegocio.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "system_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 150)
    private String clave;

    @Column(nullable = false, columnDefinition = "JSON")
    private String valor;

    @Column(length = 300)
    private String descripcion;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;
}
