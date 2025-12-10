package com.minegocio.backend.controller;

import com.minegocio.backend.dto.modulo.ModuloResponse;
import com.minegocio.backend.service.ModuloInstalacionService;
import com.minegocio.backend.service.ModuloService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios/modulos")
public class UsuarioModuloController {

    private final ModuloService moduloService;
    private final ModuloInstalacionService moduloInstalacionService;

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<ModuloResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(moduloService.obtenerPorId(id));
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<ModuloResponse>> obtenerTodos() {
        return ResponseEntity.ok(moduloService.obtenerTodos());
    }

    // Obtener por clave
    @GetMapping("/clave/{clave}")
    public ResponseEntity<ModuloResponse> obtenerPorClave(@PathVariable String clave) {
        return ResponseEntity.ok(moduloService.obtenerPorClave(clave));
    }

    // Instalar módulo
    @PostMapping("/instalar")
    public ResponseEntity<Void> instalarModulo(
            @RequestParam Integer idFranquicia,
            @RequestParam Integer idUsuario,
            @RequestParam String claveModulo,
            @RequestParam String nombreTabla) {

        moduloInstalacionService.instalarModulo(idFranquicia, idUsuario, claveModulo, nombreTabla);
        return ResponseEntity.ok().build();
    }
}
