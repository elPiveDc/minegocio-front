package com.minegocio.backend.controller;

import com.minegocio.backend.dto.franquicia.FranquiciaRequestDTO;
import com.minegocio.backend.dto.franquicia.FranquiciaResponseDTO;
import com.minegocio.backend.dto.franquiciausuario.FranquiciaUsuarioResponse;
import com.minegocio.backend.service.FranquiciaOrquestadorService;
import com.minegocio.backend.service.FranquiciaService;
import com.minegocio.backend.service.FranquiciaUsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios/franquicia")
@RequiredArgsConstructor
public class UsuarioFranquiciaController {

    private final FranquiciaService service;
    private final FranquiciaUsuarioService Uservice;
    private final FranquiciaOrquestadorService franquiciaOrquestadorService;

    @PostMapping
    public ResponseEntity<?> registrarFranquicia(@Valid @RequestBody FranquiciaRequestDTO request) {

        franquiciaOrquestadorService.registrarFranquiciaCompleta(request);

        return ResponseEntity.ok().body(
                "Franquicia creada completamente de forma exitosa.");
    }

    // ----------------------------------------------------
    // UPDATE
    // ----------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<FranquiciaResponseDTO> actualizar(
            @PathVariable Integer id,
            @RequestBody FranquiciaRequestDTO dto) {

        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    // ----------------------------------------------------
    // GET BY ID
    // ----------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<FranquiciaResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/franquicia/{id}")
    public ResponseEntity<List<FranquiciaUsuarioResponse>> obtenerusuarioPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(Uservice.obtenerPorFranquiciaId(id));
    }

    // ----------------------------------------------------
    // GET BY ID Usuario
    // ----------------------------------------------------

    @GetMapping("/listfranquicia/{id}")
    public ResponseEntity<List<FranquiciaResponseDTO>> obtenerlistaFranquiciasPorIdUsuario(@PathVariable Integer id) {
        return ResponseEntity.ok(Uservice.listarFranquiciasPorUsuario(id));
    }

    // ----------------------------------------------------
    // GET BY SLUG
    // ----------------------------------------------------
    @GetMapping("/slug/{slug}")
    public ResponseEntity<FranquiciaResponseDTO> obtenerPorSlug(@PathVariable String slug) {
        return ResponseEntity.ok(service.obtenerPorSlug(slug));
    }

    // ----------------------------------------------------
    // DELETE
    // ----------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ----------------------------------------------------
    // VALIDAR NOMBRE
    // ----------------------------------------------------
    @GetMapping("/validar/nombre")
    public ResponseEntity<Boolean> validarNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(service.existeNombre(nombre));
    }

    // ----------------------------------------------------
    // VALIDAR SLUG
    // ----------------------------------------------------
    @GetMapping("/validar/slug")
    public ResponseEntity<Boolean> validarSlug(@RequestParam String slug) {
        return ResponseEntity.ok(service.existeSlug(slug));
    }

    // ----------------------------------------------------
    // CAMBIAR ESTADO
    // ----------------------------------------------------
    @PatchMapping("/{id}/estado")
    public ResponseEntity<FranquiciaResponseDTO> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam String estado) {

        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }

}
