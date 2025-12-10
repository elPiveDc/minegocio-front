package com.minegocio.backend.service.impl;

import com.minegocio.backend.dto.objetobdfranquicia.ObjetoBdFranquiciaRequest;
import com.minegocio.backend.dto.objetobdfranquicia.ObjetoBdFranquiciaResponse;
import com.minegocio.backend.model.BaseDatosFranquicia;
import com.minegocio.backend.model.Modulo;
import com.minegocio.backend.model.ObjetoBdFranquicia;
import com.minegocio.backend.repository.ObjetoBdFranquiciaRepository;
import com.minegocio.backend.repository.BaseDatosFranquiciaRepository; // Asumido
import com.minegocio.backend.repository.ModuloRepository; // Asumido
import com.minegocio.backend.service.ObjetoBdFranquiciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp; // Aunque los campos son auto-generados, es bueno para la coherencia
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObjetoBdFranquiciaServiceImpl implements ObjetoBdFranquiciaService {

    private final ObjetoBdFranquiciaRepository objetoBdFranquiciaRepository;
    private final BaseDatosFranquiciaRepository baseDatosFranquiciaRepository; // Necesario
    private final ModuloRepository moduloRepository; // Necesario

    // --- Mappers ---

    /**
     * Convierte un ObjetoBdFranquicia a ObjetoBdFranquiciaResponse.
     */
    private ObjetoBdFranquiciaResponse toResponse(ObjetoBdFranquicia entidad) {
        return ObjetoBdFranquiciaResponse.builder()
                .id(entidad.getId())
                .idBaseDatos(entidad.getBaseDatos().getId())
                // Si modulo es null, devuelve null, si no, devuelve su ID
                .idModulo(entidad.getModulo() != null ? entidad.getModulo().getId() : null)
                .nombreTabla(entidad.getNombreTabla())
                .tipoObjeto(entidad.getTipoObjeto())
                .esTablaUsuarios(entidad.getEsTablaUsuarios())
                .columnas(entidad.getColumnas())
                .editable(entidad.getEditable())
                .fechaCreacion(entidad.getFechaCreacion())
                .createdBy(entidad.getCreatedBy())
                .updatedBy(entidad.getUpdatedBy())
                .updatedAt(entidad.getUpdatedAt())
                .build();
    }

    /**
     * Busca las entidades relacionadas y mapea el Request al objeto
     * ObjetoBdFranquicia.
     */
    private ObjetoBdFranquicia toEntity(ObjetoBdFranquiciaRequest request) {
        // 1. Obtener BaseDatosFranquicia (Obligatorio)
        BaseDatosFranquicia bd = baseDatosFranquiciaRepository.findById(request.getIdBaseDatos())
                .orElseThrow(() -> new RuntimeException(
                        "BaseDatosFranquicia no encontrada con ID: " + request.getIdBaseDatos()));

        // 2. Obtener Modulo (Opcional)
        Modulo modulo = null;
        if (request.getIdModulo() != null) {
            modulo = moduloRepository.findById(request.getIdModulo())
                    .orElseThrow(() -> new RuntimeException("Módulo no encontrado con ID: " + request.getIdModulo()));
        }

        // 3. Devolver la entidad
        return ObjetoBdFranquicia.builder()
                .baseDatos(bd)
                .modulo(modulo)
                .nombreTabla(request.getNombreTabla())
                .tipoObjeto(request.getTipoObjeto())
                .esTablaUsuarios(request.getEsTablaUsuarios())
                .columnas(request.getColumnas())
                .editable(request.getEditable())
                .createdBy(request.getCreatedBy())
                // No configuramos fechaCreacion, updatedBy ni updatedAt, ya que los maneja
                // JPA/BD o el método save
                .build();
    }

    // --- Implementación de Servicio ---

    @Override
    public ObjetoBdFranquiciaResponse crear(ObjetoBdFranquiciaRequest request) {
        // Validación de unicidad: nombre_tabla + id_bd
        if (objetoBdFranquiciaRepository.existsByNombreTablaAndBaseDatosFranquiciaId(request.getNombreTabla(),
                request.getIdBaseDatos())) {
            throw new RuntimeException("El objeto/tabla '" + request.getNombreTabla()
                    + "' ya existe en la Base de Datos ID: " + request.getIdBaseDatos());
        }

        ObjetoBdFranquicia nuevaEntidad = toEntity(request);
        // Nota: Al usar save, JPA maneja la asignación de fechaCreacion
        // (insertable=false, updatable=false)
        ObjetoBdFranquicia guardada = objetoBdFranquiciaRepository.save(nuevaEntidad);
        return toResponse(guardada);
    }

    @Override
    public ObjetoBdFranquiciaResponse obtenerPorId(Integer id) {
        ObjetoBdFranquicia entidad = objetoBdFranquiciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ObjetoBdFranquicia no encontrado con ID: " + id));
        return toResponse(entidad);
    }

    @Override
    public ObjetoBdFranquiciaResponse actualizar(Integer id, ObjetoBdFranquiciaRequest request) {
        ObjetoBdFranquicia entidadExistente = objetoBdFranquiciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ObjetoBdFranquicia no encontrado con ID: " + id));

        // Validación de unicidad si la tabla o la base de datos cambian
        if (!entidadExistente.getNombreTabla().equals(request.getNombreTabla()) ||
                !entidadExistente.getBaseDatos().getId().equals(request.getIdBaseDatos())) {

            if (objetoBdFranquiciaRepository.existsByNombreTablaAndBaseDatosFranquiciaId(request.getNombreTabla(),
                    request.getIdBaseDatos())) {
                throw new RuntimeException("La nueva combinación Tabla/Base de Datos ya existe.");
            }
        }

        // Obtener y validar nuevas entidades relacionadas (reusamos la lógica de
        // búsqueda de toEntity)
        BaseDatosFranquicia bd = baseDatosFranquiciaRepository.findById(request.getIdBaseDatos())
                .orElseThrow(() -> new RuntimeException(
                        "BaseDatosFranquicia no encontrada con ID: " + request.getIdBaseDatos()));

        Modulo modulo = null;
        if (request.getIdModulo() != null) {
            modulo = moduloRepository.findById(request.getIdModulo())
                    .orElseThrow(() -> new RuntimeException("Módulo no encontrado con ID: " + request.getIdModulo()));
        }

        // Aplicar cambios
        entidadExistente.setBaseDatos(bd);
        entidadExistente.setModulo(modulo);
        entidadExistente.setNombreTabla(request.getNombreTabla());
        entidadExistente.setTipoObjeto(request.getTipoObjeto());
        entidadExistente.setEsTablaUsuarios(request.getEsTablaUsuarios());
        entidadExistente.setColumnas(request.getColumnas());
        entidadExistente.setEditable(request.getEditable());
        entidadExistente.setUpdatedBy(request.getUpdatedBy());
        // fechaCreacion y createdBy NO se actualizan

        ObjetoBdFranquicia actualizada = objetoBdFranquiciaRepository.save(entidadExistente);
        // Nota: JPA maneja la actualización de updatedAt (insertable=false)
        return toResponse(actualizada);
    }

    @Override
    public void eliminar(Integer id) {
        if (!objetoBdFranquiciaRepository.existsById(id)) {
            throw new RuntimeException("ObjetoBdFranquicia no encontrado con ID: " + id);
        }
        objetoBdFranquiciaRepository.deleteById(id);
    }

    @Override
    public List<ObjetoBdFranquiciaResponse> obtenerTodos() {
        return objetoBdFranquiciaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // --- Implementaciones de Repositorio ---

    @Override
    public List<ObjetoBdFranquiciaResponse> obtenerPorBaseDatosId(Integer idBd) {
        return objetoBdFranquiciaRepository.findByBaseDatosFranquiciaId(idBd).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorNombreTablaYBaseDatosId(String nombreTabla, Integer idBd) {
        return objetoBdFranquiciaRepository.existsByNombreTablaAndBaseDatosFranquiciaId(nombreTabla, idBd);
    }
}