package com.minegocio.backend.controller;

import com.minegocio.backend.dto.consulta.*;
import com.minegocio.backend.dto.documento.*;
import com.minegocio.backend.dto.faq.FaqRequest;
import com.minegocio.backend.dto.faq.FaqResponse;
import com.minegocio.backend.dto.franquicia.FranquiciaResponseDTO;
import com.minegocio.backend.dto.modulo.ModuloRequest;
import com.minegocio.backend.dto.modulo.ModuloResponse;
import com.minegocio.backend.service.FaqService;
import com.minegocio.backend.service.FranquiciaService;
import com.minegocio.backend.service.ModuloService;
import com.minegocio.backend.service.ConsultaService;
import com.minegocio.backend.service.DocumentoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final FaqService faqService;
    private final DocumentoService documentoService;
    private final ModuloService moduloService;
    private final FranquiciaService service;
    private final ConsultaService consultaService;

    // -------------------------------------------------------------------------
    // FAQ Management Endpoints (/api/admin/faqs)
    // -------------------------------------------------------------------------
    @PostMapping("/faqs")
    public ResponseEntity<FaqResponse> crearFaq(@Valid @RequestBody FaqRequest request) {
        FaqResponse response = faqService.crear(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/faqs/{id}")
    public ResponseEntity<FaqResponse> actualizarFaq(@PathVariable Long id, @Valid @RequestBody FaqRequest request) {
        FaqResponse response = faqService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/faqs/{id}")
    public ResponseEntity<Void> eliminarFaq(@PathVariable Long id) {
        faqService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // -------------------------------------------------------------------------
    // Documento Management Endpoints (/api/admin/documentos)
    // -------------------------------------------------------------------------

    @PostMapping("/documentos")
    public ResponseEntity<DocumentoResponse> crearDocumento(@Valid @RequestBody DocumentoRequest request) {
        DocumentoResponse response = documentoService.crear(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/documentos/todos")
    public ResponseEntity<List<DocumentoMetadataResponse>> obtenerTodosDocumentos() {
        List<DocumentoMetadataResponse> response = documentoService.obtenerTodos();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/documentos/{id}")
    public ResponseEntity<DocumentoResponse> actualizarDocumento(@PathVariable Long id,
            @Valid @RequestBody DocumentoRequest request) {
        DocumentoResponse response = documentoService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/documentos/{id}")
    public ResponseEntity<Void> eliminarDocumento(@PathVariable Long id) {
        documentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // -----------------------------------------------------------------------
    // Modulos
    // -----------------------------------------------------------------------

    @PutMapping("/modulos/{id}")
    public ResponseEntity<ModuloResponse> actualizar(@PathVariable Integer id, @RequestBody ModuloRequest request) {
        return ResponseEntity.ok(moduloService.actualizar(id, request));
    }

    @DeleteMapping("/modulos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        moduloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ----------------------------------------------------
    // LISTA de Franquicias
    // ----------------------------------------------------
    @GetMapping("lisatar-franquicias")
    public ResponseEntity<List<FranquiciaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ----------------------------------------------------
    // Consultas
    // ----------------------------------------------------

    @GetMapping("/consultas")
    public List<ConsultaResponseDto> obtenerTodas() {
        return consultaService.obtenerTodos();
    }

    @GetMapping("/consultas/estado/{estado}")
    public List<ConsultaResponseDto> obtenerPorEstado(@PathVariable String estado) {
        return consultaService.obtenerPorEstado(estado);
    }

    @PutMapping("/consultas/{id}")
    public ConsultaResponseDto actualizarConsulta(
            @PathVariable Integer id,
            @Valid @RequestBody ConsultaUpdateDto dto) {
        return consultaService.actualizarConsulta(id, dto);
    }

    @PutMapping("/consultas/{id}/estado")
    public ConsultaResponseDto cambiarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody ConsultaEstadoDto dto) {
        return consultaService.cambiarEstado(id, dto);
    }

    @PutMapping("/consultas/{id}/responder")
    public ConsultaResponseDto responder(
            @PathVariable Integer id,
            @Valid @RequestBody ConsultaRespuestaDto dto) {
        return consultaService.responderConsulta(id, dto);
    }

    @DeleteMapping("/consultas/{id}")
    public void eliminarConsultas(@PathVariable Integer id) {
        consultaService.eliminarConsulta(id);
    }

}