package com.minegocio.backend.controller;

import com.minegocio.backend.dto.invitation.InvitationRequestDTO;
import com.minegocio.backend.dto.invitation.InvitationResponseDTO;
import com.minegocio.backend.service.InvitationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios/invitacion")
@RequiredArgsConstructor
public class UsuarioInvitationController {

    private final InvitationService invitationService;

    @PostMapping("/crear")
    public ResponseEntity<InvitationResponseDTO> crearInvitacion(@Valid @RequestBody InvitationRequestDTO request) {
        InvitationResponseDTO response = invitationService.crearInvitacion(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<InvitationResponseDTO> obtenerPorToken(@PathVariable String token) {
        InvitationResponseDTO response = invitationService.obtenerPorToken(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{token}/accept")
    public ResponseEntity<InvitationResponseDTO> aceptarInvitacion(@PathVariable String token) {
        InvitationResponseDTO response = invitationService.aceptarInvitacion(token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/franquicia/{idFranquicia}")
    public ResponseEntity<List<InvitationResponseDTO>> listarInvitacionesPorFranquicia(
            @PathVariable Integer idFranquicia) {
        List<InvitationResponseDTO> response = invitationService.listarPorFranquicia(idFranquicia);
        return ResponseEntity.ok(response);
    }
}