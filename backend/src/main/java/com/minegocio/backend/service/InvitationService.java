package com.minegocio.backend.service;

import com.minegocio.backend.dto.invitation.InvitationRequestDTO;
import com.minegocio.backend.dto.invitation.InvitationResponseDTO;

import java.util.List;

public interface InvitationService {

    InvitationResponseDTO crearInvitacion(InvitationRequestDTO request);

    InvitationResponseDTO obtenerPorToken(String token);

    List<InvitationResponseDTO> listarPorFranquicia(Integer idFranquicia);

    List<InvitationResponseDTO> listarPorEmail(String email);

    InvitationResponseDTO aceptarInvitacion(String token);
}
