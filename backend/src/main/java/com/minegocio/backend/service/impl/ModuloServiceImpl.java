package com.minegocio.backend.service.impl;

import com.minegocio.backend.dto.modulo.ModuloRequest;
import com.minegocio.backend.dto.modulo.ModuloResponse;
import com.minegocio.backend.model.Modulo;
import com.minegocio.backend.repository.ModuloRepository;
import com.minegocio.backend.service.ModuloService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuloServiceImpl implements ModuloService {

    private final ModuloRepository moduloRepository;

    // --- Mappers ---

    /**
     * Convierte un Modulo a ModuloResponse.
     * 
     * @param entidad La entidad a convertir.
     * @return El DTO de respuesta.
     */
    private ModuloResponse toResponse(Modulo entidad) {
        return ModuloResponse.builder()
                .id(entidad.getId())
                .clave(entidad.getClave())
                .nombre(entidad.getNombre())
                .descripcion(entidad.getDescripcion())
                .createdAt(entidad.getCreatedAt())
                .build();
    }

    /**
     * Convierte un ModuloRequest a Modulo (para creación/actualización).
     * 
     * @param request El DTO de request.
     * @return La nueva entidad Modulo.
     */
    private Modulo toEntity(ModuloRequest request) {
        return Modulo.builder()
                .clave(request.getClave())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .build();
    }

    // --- Implementación de Servicio ---

    @Override
    public ModuloResponse crear(ModuloRequest request) {
        // Validación de unicidad de la clave
        if (moduloRepository.findByClave(request.getClave()).isPresent()) {
            throw new RuntimeException("La clave del módulo ya está registrada: " + request.getClave());
        }

        Modulo nuevaEntidad = toEntity(request);
        Modulo guardada = moduloRepository.save(nuevaEntidad);
        return toResponse(guardada);
    }

    @Override
    public ModuloResponse obtenerPorId(Integer id) {
        Modulo entidad = moduloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado con ID: " + id));
        return toResponse(entidad);
    }

    @Override
    public ModuloResponse actualizar(Integer id, ModuloRequest request) {
        Modulo entidadExistente = moduloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado con ID: " + id));

        // Validación de unicidad de la clave (solo si la clave cambia)
        if (!entidadExistente.getClave().equals(request.getClave())) {
            if (moduloRepository.findByClave(request.getClave()).isPresent()) {
                throw new RuntimeException("La nueva clave ya está registrada: " + request.getClave());
            }
        }

        // Aplicar cambios
        entidadExistente.setClave(request.getClave());
        entidadExistente.setNombre(request.getNombre());
        entidadExistente.setDescripcion(request.getDescripcion());
        // El ID y createdAt NO se actualizan

        Modulo actualizada = moduloRepository.save(entidadExistente);
        return toResponse(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        if (!moduloRepository.existsById(id)) {
            throw new RuntimeException("Módulo no encontrado con ID: " + id);
        }
        moduloRepository.deleteById(id);
    }

    @Override
    public List<ModuloResponse> obtenerTodos() {
        return moduloRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // --- Implementación específica del Repositorio ---

    @Override
    public ModuloResponse obtenerPorClave(String clave) {
        Modulo entidad = moduloRepository.findByClave(clave)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado con clave: " + clave));
        return toResponse(entidad);
    }

}