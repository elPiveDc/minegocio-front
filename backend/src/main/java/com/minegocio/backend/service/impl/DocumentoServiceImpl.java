package com.minegocio.backend.service.impl;

import com.minegocio.backend.dto.documento.DocumentoMetadataResponse;
import com.minegocio.backend.dto.documento.DocumentoRequest;
import com.minegocio.backend.dto.documento.DocumentoResponse;
import com.minegocio.backend.model.Documento;
import com.minegocio.backend.repository.DocumentoRepository;
import com.minegocio.backend.service.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;

    // --- Mappers ---

    /**
     * Convierte un Documento a DocumentoResponse (completo, incluye archivo).
     */
    private DocumentoResponse toResponse(Documento entidad) {
        return DocumentoResponse.builder()
                .id(entidad.getId())
                .titulo(entidad.getTitulo())
                .slug(entidad.getSlug())
                .tipoContenido(entidad.getTipoContenido())
                .archivo(entidad.getArchivo())
                .fechaSubida(entidad.getFechaSubida())
                .build();
    }

    /**
     * Convierte un Documento a DocumentoMetadataResponse (solo metadatos, sin
     * archivo).
     */
    private DocumentoMetadataResponse toMetadataResponse(Documento entidad) {
        return DocumentoMetadataResponse.builder()
                .id(entidad.getId())
                .titulo(entidad.getTitulo())
                .slug(entidad.getSlug())
                .tipoContenido(entidad.getTipoContenido())
                .fechaSubida(entidad.getFechaSubida())
                .build();
    }

    /**
     * Convierte un DocumentoRequest a Documento.
     */
    private Documento toEntity(DocumentoRequest request) {
        return Documento.builder()
                .titulo(request.getTitulo())
                .slug(request.getSlug())
                .tipoContenido(request.getTipoContenido())
                .archivo(request.getArchivo())
                .build();
    }

    // --- Implementación de Servicio ---

    @Override
    public DocumentoResponse crear(DocumentoRequest request) {
        // Validación de unicidad de slug
        if (documentoRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException("Ya existe un documento con el slug: " + request.getSlug());
        }

        Documento nuevaEntidad = toEntity(request);
        // La fechaSubida es manejada por la base de datos (insertable=false)
        Documento guardada = documentoRepository.save(nuevaEntidad);
        return toResponse(guardada);
    }

    @Override
    public DocumentoResponse obtenerPorId(Long id) {
        Documento entidad = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado con ID: " + id));
        return toResponse(entidad);
    }

    @Override
    public DocumentoResponse actualizar(Long id, DocumentoRequest request) {
        Documento entidadExistente = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado con ID: " + id));

        // Validación de unicidad de slug (si el slug cambia)
        if (!entidadExistente.getSlug().equals(request.getSlug())) {
            if (documentoRepository.existsBySlug(request.getSlug())) {
                throw new RuntimeException("El nuevo slug ya está en uso: " + request.getSlug());
            }
        }

        // Aplicar cambios
        entidadExistente.setTitulo(request.getTitulo());
        entidadExistente.setSlug(request.getSlug());
        entidadExistente.setTipoContenido(request.getTipoContenido());
        entidadExistente.setArchivo(request.getArchivo());
        // El ID y fechaSubida NO se actualizan

        Documento actualizada = documentoRepository.save(entidadExistente);
        return toResponse(actualizada);
    }

    @Override
    public void eliminar(Long id) {
        if (!documentoRepository.existsById(id)) {
            throw new RuntimeException("Documento no encontrado con ID: " + id);
        }
        documentoRepository.deleteById(id);
    }

    @Override
    public List<DocumentoMetadataResponse> obtenerTodos() {
        // Devolvemos solo metadatos para evitar la transferencia de grandes archivos
        // binarios
        return documentoRepository.findAll().stream()
                .map(this::toMetadataResponse)
                .collect(Collectors.toList());
    }

    // --- Implementaciones de Repositorio ---

    @Override
    public DocumentoResponse obtenerPorSlug(String slug) {
        Documento entidad = documentoRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado con slug: " + slug));
        return toResponse(entidad);
    }

    @Override
    public boolean existePorSlug(String slug) {
        return documentoRepository.existsBySlug(slug);
    }

    @Override
    public List<DocumentoMetadataResponse> buscarPorTitulo(String titulo) {
        return documentoRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(this::toMetadataResponse)
                .collect(Collectors.toList());
    }
}