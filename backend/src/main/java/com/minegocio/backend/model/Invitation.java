package com.minegocio.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

import com.minegocio.backend.model.enums.InvitationStatus;
import com.minegocio.backend.model.enums.RoleOffered;

@Entity
@Table(name = "invitations", indexes = {
        @Index(name = "idx_inv_email", columnList = "invited_email"),
        @Index(name = "idx_inv_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invitation")
    private Long id;

    // RELACIÓN CON FRANQUICIA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_franquicia", nullable = false, foreignKey = @ForeignKey(name = "fk_inv_frn"))
    private Franquicia franquicia;

    @Column(name = "invited_email", nullable = false, length = 255)
    private String invitedEmail;

    // RELACIÓN CON usuarios (puede ser null por ON DELETE SET NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invited_by", foreignKey = @ForeignKey(name = "fk_inv_by"))
    private Usuario invitedBy;

    @Column(nullable = false, unique = true, length = 255)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_offered", nullable = false, columnDefinition = "ENUM('OWNER','CO_OWNER','ADMIN','MANAGER','EMPLOYEE','VIEWER')")
    private RoleOffered roleOffered = RoleOffered.EMPLOYEE;

    @Column(name = "expires_at")
    private Timestamp expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('PENDING','ACCEPTED','REVOKED','EXPIRED')")
    private InvitationStatus status = InvitationStatus.PENDING;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "accepted_at")
    private Timestamp acceptedAt;

}
