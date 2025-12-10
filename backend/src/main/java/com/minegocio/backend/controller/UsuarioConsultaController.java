package com.minegocio.backend.controller;

import com.minegocio.backend.dto.consulta.*;
import com.minegocio.backend.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/usuario/consultas")
@RequiredArgsConstructor
public class UsuarioConsultaController {

    private final ConsultaService consultaService;

    @PostMapping
    public ConsultaResponseDto crearConsulta(@Valid @RequestBody ConsultaCreateDto dto) {
        return consultaService.crearConsulta(dto);
    }

    @GetMapping("/{idUsuario}")
    public List<ConsultaResponseDto> obtenerMisConsultas(@PathVariable Integer idUsuario) {
        return consultaService.obtenerPorUsuario(idUsuario);
    }

    @GetMapping("/detalle/{idConsulta}")
    public ConsultaResponseDto obtenerDetalle(@PathVariable Integer idConsulta) {
        return consultaService.obtenerPorId(idConsulta);
    }
}
