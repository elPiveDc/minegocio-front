package com.minegocio.backend.service;

import com.minegocio.backend.dto.faq.FaqRequest;
import com.minegocio.backend.dto.faq.FaqResponse;

import java.util.List;

public interface FaqService {

    // Operaciones CRUD
    FaqResponse crear(FaqRequest request);

    FaqResponse obtenerPorId(Long id);

    FaqResponse actualizar(Long id, FaqRequest request);

    void eliminar(Long id);

    List<FaqResponse> obtenerTodos();

    // Operaciones de búsqueda específicas del repositorio
    List<FaqResponse> buscarFullText(String textoBusqueda);

    List<FaqResponse> buscarFullTextBooleano(String textoBusqueda);

    List<FaqResponse> filtrarPorPregunta(String pregunta);
}