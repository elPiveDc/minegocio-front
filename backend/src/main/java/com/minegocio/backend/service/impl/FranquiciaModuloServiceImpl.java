package com.minegocio.backend.service.impl;

import com.minegocio.backend.dto.franquiciamodulo.FranquiciaModuloRequestDTO;
import com.minegocio.backend.dto.franquiciamodulo.FranquiciaModuloResponseDTO;
import com.minegocio.backend.model.Franquicia;
import com.minegocio.backend.model.Modulo;
import com.minegocio.backend.model.FranquiciaModulo;
import com.minegocio.backend.repository.FranquiciaModuloRepository;
import com.minegocio.backend.repository.FranquiciaRepository;
import com.minegocio.backend.repository.ModuloRepository;
import com.minegocio.backend.service.FranquiciaModuloService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FranquiciaModuloServiceImpl implements FranquiciaModuloService {

    private final FranquiciaModuloRepository repository;
    private final FranquiciaRepository franquiciaRepository;
    private final ModuloRepository moduloRepository;

    // ----------------------------------------------------
    // MAPPER INTERNO
    // ----------------------------------------------------

    private FranquiciaModulo toEntity(FranquiciaModuloRequestDTO dto, Franquicia franquicia, Modulo modulo) {
        return FranquiciaModulo.builder()
                .franquicia(franquicia)
                .modulo(modulo)
                .activo(dto.getActivo())
                .configuracion(dto.getConfiguracion())
                .build();
    }

    private FranquiciaModuloResponseDTO toDTO(FranquiciaModulo entity) {
        return FranquiciaModuloResponseDTO.builder()
                .id(entity.getId())
                .franquiciaId(entity.getFranquicia().getId())
                .moduloId(entity.getModulo().getId())
                .activo(entity.getActivo())
                .configuracion(entity.getConfiguracion())
                .fechaInstalacion(entity.getFechaInstalacion())
                .build();
    }

    // ----------------------------------------------------
    // LÓGICA DEL SERVICIO
    // ----------------------------------------------------

    @Override
    public FranquiciaModuloResponseDTO asignarModulo(FranquiciaModuloRequestDTO dto) {

        if (repository.existsByFranquiciaIdAndModuloId(dto.getFranquiciaId(), dto.getModuloId())) {
            throw new IllegalArgumentException("Este módulo ya está asignado a la franquicia.");
        }

        Franquicia franquicia = franquiciaRepository.findById(dto.getFranquiciaId())
                .orElseThrow(() -> new IllegalArgumentException("Franquicia no encontrada."));

        Modulo modulo = moduloRepository.findById(dto.getModuloId())
                .orElseThrow(() -> new IllegalArgumentException("Modulo no encontrado."));

        FranquiciaModulo entity = toEntity(dto, franquicia, modulo);

        repository.save(entity);

        return toDTO(entity);
    }

    @Override
    public FranquiciaModuloResponseDTO actualizar(Integer id, FranquiciaModuloRequestDTO dto) {

        FranquiciaModulo entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FranquiciaModulo no encontrado."));

        Franquicia franquicia = franquiciaRepository.findById(dto.getFranquiciaId())
                .orElseThrow(() -> new IllegalArgumentException("Franquicia no encontrada."));

        Modulo modulo = moduloRepository.findById(dto.getModuloId())
                .orElseThrow(() -> new IllegalArgumentException("Modulo no encontrado."));

        // Validación de unique constraint en caso de update
        if (!entity.getFranquicia().getId().equals(dto.getFranquiciaId()) ||
                !entity.getModulo().getId().equals(dto.getModuloId())) {

            if (repository.existsByFranquiciaIdAndModuloId(dto.getFranquiciaId(), dto.getModuloId())) {
                throw new IllegalArgumentException("La franquicia ya tiene asignado este módulo.");
            }
        }

        entity.setFranquicia(franquicia);
        entity.setModulo(modulo);
        entity.setActivo(dto.getActivo());
        entity.setConfiguracion(dto.getConfiguracion());

        repository.save(entity);

        return toDTO(entity);
    }

    @Override
    public FranquiciaModuloResponseDTO obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Registro no encontrado."));
    }

    @Override
    public List<FranquiciaModuloResponseDTO> listarPorFranquicia(Integer franquiciaId) {
        return repository.findByFranquiciaId(franquiciaId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public FranquiciaModuloResponseDTO obtenerPorFranquiciaYModulo(Integer franquiciaId, Integer moduloId) {
        return repository.findByFranquiciaIdAndModuloId(franquiciaId, moduloId)
                .map(this::toDTO)
                .orElseThrow(
                        () -> new IllegalArgumentException("No existe asignación de este módulo a la franquicia."));
    }

    @Override
    public void eliminar(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Registro no encontrado.");
        }
        repository.deleteById(id);
    }
}
