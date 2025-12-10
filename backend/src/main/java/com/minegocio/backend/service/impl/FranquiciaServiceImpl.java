package com.minegocio.backend.service.impl;

import com.minegocio.backend.dto.franquicia.FranquiciaRequestDTO;
import com.minegocio.backend.dto.franquicia.FranquiciaResponseDTO;
import com.minegocio.backend.model.Franquicia;
import com.minegocio.backend.model.enums.EstadoFranquicia;
import com.minegocio.backend.repository.FranquiciaRepository;
import com.minegocio.backend.service.FranquiciaService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FranquiciaServiceImpl implements FranquiciaService {

    private final FranquiciaRepository repository;

    // ----------------------------------------------------
    // MÉTODOS DEL SERVICIO
    // ----------------------------------------------------

    @Override
    public FranquiciaResponseDTO crear(FranquiciaRequestDTO dto) {

        if (repository.existsByNombreFranquicia(dto.getNombreFranquicia())) {
            throw new IllegalArgumentException("El nombre ya está registrado.");
        }

        if (repository.existsBySlug(dto.getSlug())) {
            throw new IllegalArgumentException("El slug ya está en uso.");
        }

        Franquicia entity = toEntity(dto);
        entity.setFechaCreacion(LocalDateTime.now());

        repository.save(entity);
        return toDTO(entity);
    }

    @Override
    public FranquiciaResponseDTO actualizar(Integer id, FranquiciaRequestDTO dto) {
        Franquicia entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Franquicia no encontrada."));

        entity.setNombreFranquicia(dto.getNombreFranquicia());
        entity.setSlug(dto.getSlug());
        entity.setDescripcion(dto.getDescripcion());
        entity.setTemplateId(dto.getTemplateId());

        repository.save(entity);
        return toDTO(entity);
    }

    @Override
    public FranquiciaResponseDTO obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Franquicia no encontrada."));
    }

    @Override
    public FranquiciaResponseDTO obtenerPorSlug(String slug) {
        Franquicia entity = repository.findAll().stream()
                .filter(f -> f.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Slug no encontrado."));

        return toDTO(entity);
    }

    @Override
    public List<FranquiciaResponseDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public void eliminar(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Franquicia no encontrada.");
        }
        repository.deleteById(id);
    }

    @Override
    public boolean existeNombre(String nombreFranquicia) {
        return repository.existsByNombreFranquicia(nombreFranquicia);
    }

    @Override
    public boolean existeSlug(String slug) {
        return repository.existsBySlug(slug);
    }

    @Override
    public FranquiciaResponseDTO cambiarEstado(Integer id, String nuevoEstado) {
        Franquicia entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Franquicia no encontrada."));

        entity.setEstado(EstadoFranquicia.valueOf(nuevoEstado.toUpperCase()));

        repository.save(entity);
        return toDTO(entity);
    }

    // ----------------------------------------------------
    // MAPPER INTERNO: solo lo usa este servicio
    // ----------------------------------------------------
    private Franquicia toEntity(FranquiciaRequestDTO dto) {
        return Franquicia.builder()
                .nombreFranquicia(dto.getNombreFranquicia())
                .slug(dto.getSlug())
                .descripcion(dto.getDescripcion())
                .templateId(dto.getTemplateId())
                .build();
    }

    private FranquiciaResponseDTO toDTO(Franquicia entity) {
        return FranquiciaResponseDTO.builder()
                .id(entity.getId())
                .nombreFranquicia(entity.getNombreFranquicia())
                .slug(entity.getSlug())
                .descripcion(entity.getDescripcion())
                .fechaCreacion(entity.getFechaCreacion())
                .estado(entity.getEstado())
                .templateId(entity.getTemplateId())
                .build();
    }

}
