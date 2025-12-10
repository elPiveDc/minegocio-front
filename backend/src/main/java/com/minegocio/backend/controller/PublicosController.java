package com.minegocio.backend.controller;

import com.minegocio.backend.dto.documento.DocumentoResponse;
import com.minegocio.backend.dto.faq.FaqResponse;
import com.minegocio.backend.service.FaqService;
import com.minegocio.backend.service.DocumentoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicos")
@RequiredArgsConstructor
public class PublicosController {

    private final FaqService faqService;
    private final DocumentoService documentoService;

    // -------------------------------------------------------------------------
    // FAQ Endpoints
    // -------------------------------------------------------------------------

    @GetMapping("/faqs")
    public ResponseEntity<List<FaqResponse>> obtenerTodasFaqs() {
        return ResponseEntity.ok(faqService.obtenerTodos());
    }

    @GetMapping("/faqs/buscar")
    public ResponseEntity<List<FaqResponse>> buscarFaqsPorPregunta(@RequestParam String pregunta) {
        return ResponseEntity.ok(faqService.filtrarPorPregunta(pregunta));
    }

    // -------------------------------------------------------------------------
    // Documentos Públicos (Por Slug Específico)
    // -------------------------------------------------------------------------

    @GetMapping("/documentos/terminos-condiciones")
    public ResponseEntity<DocumentoResponse> obtenerTerminosCondiciones() {
        return ResponseEntity.ok(documentoService.obtenerPorSlug("terminos-condiciones"));
    }

    @GetMapping("/documentos/politica-privacidad")
    public ResponseEntity<DocumentoResponse> obtenerPoliticaPrivacidad() {
        return ResponseEntity.ok(documentoService.obtenerPorSlug("politica-privacidad"));
    }
}