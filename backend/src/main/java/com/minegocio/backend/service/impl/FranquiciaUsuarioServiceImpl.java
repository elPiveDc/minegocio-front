package com.minegocio.backend.service.impl;

import com.minegocio.backend.dto.franquicia.FranquiciaResponseDTO;
import com.minegocio.backend.dto.franquiciausuario.FranquiciaUsuarioRequest;
import com.minegocio.backend.dto.franquiciausuario.FranquiciaUsuarioResponse;
import com.minegocio.backend.model.Franquicia;
import com.minegocio.backend.model.FranquiciaUsuario;
import com.minegocio.backend.model.Usuario;
import com.minegocio.backend.model.enums.FranquiciaUsuarioRol;
import com.minegocio.backend.repository.FranquiciaUsuarioRepository;
import com.minegocio.backend.repository.FranquiciaRepository;
import com.minegocio.backend.repository.UsuarioRepository;
import com.minegocio.backend.service.FranquiciaUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FranquiciaUsuarioServiceImpl implements FranquiciaUsuarioService {

    private final FranquiciaUsuarioRepository franquiciaUsuarioRepository;
    private final FranquiciaRepository franquiciaRepository;
    private final UsuarioRepository usuarioRepository;

    // --- Implementación de Servicio ---

    @Override
    public FranquiciaUsuarioResponse crear(FranquiciaUsuarioRequest request) {
        // Validación de unicidad (ux_franquicia_usuario)
        if (franquiciaUsuarioRepository.existsByFranquiciaIdAndUsuarioId(request.getIdFranquicia(),
                request.getIdUsuario())) {
            throw new RuntimeException("El usuario ya está asignado a esta franquicia.");
        }

        FranquiciaUsuario nuevaEntidad = toEntity(request);
        FranquiciaUsuario guardada = franquiciaUsuarioRepository.save(nuevaEntidad);
        return toResponse(guardada);
    }

    @Override
    public FranquiciaUsuarioResponse obtenerPorId(Integer id) {
        FranquiciaUsuario entidad = franquiciaUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FranquiciaUsuario no encontrada con ID: " + id));
        return toResponse(entidad);
    }

    @Override
    public FranquiciaUsuarioResponse actualizar(Integer id, FranquiciaUsuarioRequest request) {
        FranquiciaUsuario entidadExistente = franquiciaUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FranquiciaUsuario no encontrada con ID: " + id));

        // Validación de unicidad (si los IDs de Franquicia/Usuario cambian, debe ser
        // único)
        if (!entidadExistente.getFranquicia().getId().equals(request.getIdFranquicia()) ||
                !entidadExistente.getUsuario().getId().equals(request.getIdUsuario())) {

            if (franquiciaUsuarioRepository.existsByFranquiciaIdAndUsuarioId(request.getIdFranquicia(),
                    request.getIdUsuario())) {
                throw new RuntimeException("La nueva combinación Franquicia/Usuario ya existe.");
            }
        }

        // Obtener y validar nuevas entidades relacionadas (reusamos la lógica de
        // búsqueda de toEntity)
        Franquicia franquicia = franquiciaRepository.findById(request.getIdFranquicia())
                .orElseThrow(
                        () -> new RuntimeException("Franquicia no encontrada con ID: " + request.getIdFranquicia()));

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.getIdUsuario()));

        // Aplicar cambios
        entidadExistente.setFranquicia(franquicia);
        entidadExistente.setUsuario(usuario);
        entidadExistente.setRol(request.getRol());
        entidadExistente.setEstado(request.getEstado());
        // La fechaAsignacion NO se actualiza

        FranquiciaUsuario actualizada = franquiciaUsuarioRepository.save(entidadExistente);
        return toResponse(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        if (!franquiciaUsuarioRepository.existsById(id)) {
            throw new RuntimeException("FranquiciaUsuario no encontrada con ID: " + id);
        }
        franquiciaUsuarioRepository.deleteById(id);
    }

    @Override
    public List<FranquiciaUsuarioResponse> obtenerTodos() {
        return franquiciaUsuarioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // --- Implementaciones de Repositorio ---

    @Override
    public List<FranquiciaUsuarioResponse> obtenerPorFranquiciaId(Integer idFranquicia) {
        return franquiciaUsuarioRepository.findByFranquiciaId(idFranquicia).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FranquiciaUsuarioResponse> obtenerPorUsuarioId(Integer idUsuario) {
        return franquiciaUsuarioRepository.findByUsuarioId(idUsuario).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorFranquiciaIdYUsuarioId(Integer idFranquicia, Integer idUsuario) {
        return franquiciaUsuarioRepository.existsByFranquiciaIdAndUsuarioId(idFranquicia, idUsuario);
    }

    @Override
    public FranquiciaUsuarioResponse obtenerPorFranquiciaIdYUsuarioId(Integer idFranquicia, Integer idUsuario) {
        FranquiciaUsuario entidad = franquiciaUsuarioRepository.findByFranquiciaIdAndUsuarioId(idFranquicia, idUsuario)
                .orElseThrow(() -> new RuntimeException("Relación FranquiciaUsuario no encontrada para Franquicia ID: "
                        + idFranquicia + " y Usuario ID: " + idUsuario));
        return toResponse(entidad);
    }

    @Override
    public List<FranquiciaUsuarioResponse> obtenerPorFranquiciaIdYRol(Integer idFranquicia, FranquiciaUsuarioRol rol) {
        // El método del JPA recibe String, por eso usamos rol.name()
        return franquiciaUsuarioRepository.findByFranquiciaIdAndRol(idFranquicia, rol.name()).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FranquiciaResponseDTO> listarFranquiciasPorUsuario(Integer idUsuario) {

        return franquiciaRepository.findFranquiciasByUsuarioId(idUsuario)
                .stream()
                .map(f -> FranquiciaResponseDTO.builder()
                        .id(f.getId())
                        .nombreFranquicia(f.getNombreFranquicia())
                        .slug(f.getSlug())
                        .descripcion(f.getDescripcion())
                        .fechaCreacion(f.getFechaCreacion())
                        .estado(f.getEstado())
                        .templateId(f.getTemplateId())
                        .build())
                .toList();
    }

    // --- Mappers ---

    /**
     * Convierte un FranquiciaUsuario a FranquiciaUsuarioResponse.
     * 
     * @param entidad La entidad a convertir.
     * @return El DTO de respuesta.
     */
    private FranquiciaUsuarioResponse toResponse(FranquiciaUsuario entidad) {
        return FranquiciaUsuarioResponse.builder()
                .id(entidad.getId())
                .idFranquicia(entidad.getFranquicia().getId())
                .idUsuario(entidad.getUsuario().getId())
                .rol(entidad.getRol())
                .estado(entidad.getEstado())
                .fechaAsignacion(entidad.getFechaAsignacion())
                .build();
    }

    /**
     * Busca las entidades relacionadas y mapea el Request al objeto
     * FranquiciaUsuario.
     * 
     * @param request El DTO de request.
     * @return La nueva entidad FranquiciaUsuario.
     */
    private FranquiciaUsuario toEntity(FranquiciaUsuarioRequest request) {
        // 1. Obtener Franquicia, lanzar RuntimeException si no existe
        Franquicia franquicia = franquiciaRepository.findById(request.getIdFranquicia())
                .orElseThrow(
                        () -> new RuntimeException("Franquicia no encontrada con ID: " + request.getIdFranquicia()));

        // 2. Obtener Usuario, lanzar RuntimeException si no existe
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.getIdUsuario()));

        // 3. Devolver la entidad
        return FranquiciaUsuario.builder()
                .franquicia(franquicia)
                .usuario(usuario)
                .rol(request.getRol())
                .estado(request.getEstado())
                .fechaAsignacion(new Timestamp(System.currentTimeMillis())) // Se establece la fecha al crear
                .build();
    }
}