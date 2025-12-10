package com.minegocio.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "audit_logs", indexes = {
        @Index(name = "idx_audit_actor", columnList = "actor_user_id"),
        @Index(name = "idx_audit_action", columnList = "action")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_user_id", foreignKey = @ForeignKey(name = "fk_audit_user"))
    private Usuario actor;

    @Column(nullable = false, length = 200)
    private String action;

    @Column(name = "target_type", length = 100)
    private String targetType;

    @Column(name = "target_id", length = 200)
    private String targetId;

    @Column(columnDefinition = "JSON")
    private String details;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;
}
