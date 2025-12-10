package com.minegocio.backend.service.impl;

import com.minegocio.backend.dto.faq.FaqRequest;
import com.minegocio.backend.dto.faq.FaqResponse;
import com.minegocio.backend.model.Faq;
import com.minegocio.backend.repository.FaqRepository;
import com.minegocio.backend.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqRepository faqRepository;

    // --- Mappers ---

    /**
     * Convierte un Faq a FaqResponse.
     */
    private FaqResponse toResponse(Faq entidad) {
        return FaqResponse.builder()
                .id(entidad.getId())
                .question(entidad.getQuestion())
                .answer(entidad.getAnswer())
                .createdAt(entidad.getCreatedAt())
                .updatedAt(entidad.getUpdatedAt())
                .build();
    }

    /**
     * Convierte un FaqRequest a Faq (para creación/actualización).
     */
    private Faq toEntity(FaqRequest request) {
        return Faq.builder()
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .build();
    }

    // --- Implementación de Servicio ---

    @Override
    public FaqResponse crear(FaqRequest request) {
        Faq nuevaEntidad = toEntity(request);
        Faq guardada = faqRepository.save(nuevaEntidad);
        return toResponse(guardada);
    }

    @Override
    public FaqResponse obtenerPorId(Long id) {
        Faq entidad = faqRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FAQ no encontrada con ID: " + id));
        return toResponse(entidad);
    }

    @Override
    public FaqResponse actualizar(Long id, FaqRequest request) {
        Faq entidadExistente = faqRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FAQ no encontrada con ID: " + id));

        // Aplicar cambios
        entidadExistente.setQuestion(request.getQuestion());
        entidadExistente.setAnswer(request.getAnswer());
        // El ID y createdAt NO se actualizan. updatedAt se actualiza automáticamente
        // por JPA/BD.

        Faq actualizada = faqRepository.save(entidadExistente);
        return toResponse(actualizada);
    }

    @Override
    public void eliminar(Long id) {
        if (!faqRepository.existsById(id)) {
            throw new RuntimeException("FAQ no encontrada con ID: " + id);
        }
        faqRepository.deleteById(id);
    }

    @Override
    public List<FaqResponse> obtenerTodos() {
        return faqRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // --- Implementaciones de Búsqueda de Repositorio ---

    @Override
    public List<FaqResponse> buscarFullText(String textoBusqueda) {
        return faqRepository.searchFullText(textoBusqueda).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FaqResponse> buscarFullTextBooleano(String textoBusqueda) {
        return faqRepository.searchFullTextBoolean(textoBusqueda).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FaqResponse> filtrarPorPregunta(String pregunta) {
        return faqRepository.findByQuestionContainingIgnoreCase(pregunta).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}