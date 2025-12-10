package com.minegocio.backend.dto.invitation;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class InvitationRequestDTO {
    private Integer idFranquicia;
    private String invitedEmail;
    private int invitedBy; // ID del usuario que invita
    private String roleOffered;
    private Timestamp expiresAt;
}
