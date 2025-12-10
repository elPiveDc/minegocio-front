package com.minegocio.backend.dto.invitation;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class InvitationResponseDTO {
    private Long id;
    private Integer idFranquicia;
    private String invitedEmail;
    private int invitedBy;
    private String token;
    private String roleOffered;
    private String status;
    private Timestamp expiresAt;
    private Timestamp createdAt;
    private Timestamp acceptedAt;
}
