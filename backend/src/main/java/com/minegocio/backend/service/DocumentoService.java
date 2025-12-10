package com.minegocio.backend.service;

import com.minegocio.backend.dto.documento.DocumentoRequest;
import com.minegocio.backend.dto.documento.DocumentoResponse;
import com.minegocio.backend.dto.documento.DocumentoMetadataResponse;

import java.util.List;

public interface DocumentoService {

    // Operaciones CRUD
    DocumentoResponse crear(DocumentoRequest request);

    DocumentoResponse obtenerPorId(Long id);

    DocumentoResponse actualizar(Long id, DocumentoRequest request);

    void eliminar(Long id);

    List<DocumentoMetadataResponse> obtenerTodos(); // Usamos Metadata para la lista

    // Operaciones específicas del repositorio
    DocumentoResponse obtenerPorSlug(String slug);

    boolean existePorSlug(String slug);

    List<DocumentoMetadataResponse> buscarPorTitulo(String titulo);
}