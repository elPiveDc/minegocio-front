package com.minegocio.backend.service.impl;

import com.minegocio.backend.dto.invitation.InvitationRequestDTO;
import com.minegocio.backend.dto.invitation.InvitationResponseDTO;
import com.minegocio.backend.model.*;
import com.minegocio.backend.model.enums.InvitationStatus;
import com.minegocio.backend.model.enums.RoleOffered;
import com.minegocio.backend.repository.*;
import com.minegocio.backend.service.InvitationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final FranquiciaRepository franquiciaRepository;
    private final UsuarioRepository usuarioRepository;

    /*
     * ===========================================================
     * =============== MAPPER INTERNO ===============
     * ===========================================================
     */

    private InvitationResponseDTO toDTO(Invitation inv) {
        InvitationResponseDTO dto = new InvitationResponseDTO();
        dto.setId(inv.getId());
        dto.setIdFranquicia(inv.getFranquicia().getId());
        dto.setInvitedEmail(inv.getInvitedEmail());
        dto.setInvitedBy(inv.getInvitedBy() != null ? inv.getInvitedBy().getId() : null);
        dto.setToken(inv.getToken());
        dto.setRoleOffered(inv.getRoleOffered().name());
        dto.setStatus(inv.getStatus().name());
        dto.setExpiresAt(inv.getExpiresAt());
        dto.setCreatedAt(inv.getCreatedAt());
        dto.setAcceptedAt(inv.getAcceptedAt());
        return dto;
    }

    /*
     * ===========================================================
     * =============== SERVICIOS ===============
     * ===========================================================
     */

    @Override
    @Transactional
    public InvitationResponseDTO crearInvitacion(InvitationRequestDTO request) {

        Franquicia frn = franquiciaRepository.findById(request.getIdFranquicia())
                .orElseThrow(() -> new RuntimeException("Franquicia no encontrada"));

        Usuario invitador = null;
        if (request.getInvitedBy() != 0) {
            invitador = usuarioRepository.findById(request.getInvitedBy())
                    .orElse(null);
        }

        Invitation inv = Invitation.builder()
                .franquicia(frn)
                .invitedEmail(request.getInvitedEmail())
                .invitedBy(invitador)
                .token(UUID.randomUUID().toString())
                .roleOffered(RoleOffered.valueOf(request.getRoleOffered()))
                .expiresAt(request.getExpiresAt())
                .status(InvitationStatus.PENDING)
                .build();

        invitationRepository.save(inv);

        return toDTO(inv);
    }

    @Override
    public InvitationResponseDTO obtenerPorToken(String token) {
        Invitation inv = invitationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invitación no encontrada"));
        return toDTO(inv);
    }

    @Override
    public List<InvitationResponseDTO> listarPorFranquicia(Integer idFranquicia) {
        return invitationRepository.findByFranquiciaId(idFranquicia)
                .stream().map(this::toDTO).toList();
    }

    @Override
    public List<InvitationResponseDTO> listarPorEmail(String email) {
        return invitationRepository.findByInvitedEmail(email)
                .stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional
    public InvitationResponseDTO aceptarInvitacion(String token) {
        Invitation inv = invitationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (inv.getStatus() != InvitationStatus.PENDING)
            throw new RuntimeException("La invitación no está disponible para aceptar");

        inv.setStatus(InvitationStatus.ACCEPTED);
        inv.setAcceptedAt(Timestamp.from(Instant.now()));

        invitationRepository.save(inv);

        return toDTO(inv);
    }
}
