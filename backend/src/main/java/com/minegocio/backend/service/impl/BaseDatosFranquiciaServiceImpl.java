package com.minegocio.backend.service.impl;

import com.minegocio.backend.dto.basedatos.BaseDatosFranquiciaRequestDTO;
import com.minegocio.backend.dto.basedatos.BaseDatosFranquiciaResponseDTO;
import com.minegocio.backend.model.BaseDatosFranquicia;
import com.minegocio.backend.model.Franquicia;
import com.minegocio.backend.model.enums.EstadoBaseDatos;
import com.minegocio.backend.repository.BaseDatosFranquiciaRepository;
import com.minegocio.backend.repository.FranquiciaRepository;
import com.minegocio.backend.service.BaseDatosFranquiciaService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseDatosFranquiciaServiceImpl implements BaseDatosFranquiciaService {

    private final BaseDatosFranquiciaRepository repository;
    private final FranquiciaRepository franquiciaRepository;

    // ----------------------------------------------------
    // OPERACIONES DEL SERVICIO
    // ----------------------------------------------------

    @Override
    public BaseDatosFranquiciaResponseDTO crear(BaseDatosFranquiciaRequestDTO dto) {

        Franquicia franquicia = franquiciaRepository.findById(dto.getFranquiciaId())
                .orElseThrow(() -> new IllegalArgumentException("Franquicia no encontrada."));

        BaseDatosFranquicia entity = toEntity(dto, franquicia);

        repository.save(entity);

        return toDTO(entity);
    }

    @Override
    public BaseDatosFranquiciaResponseDTO actualizar(Integer id, BaseDatosFranquiciaRequestDTO dto) {

        BaseDatosFranquicia entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registro BD no encontrado."));

        Franquicia franquicia = franquiciaRepository.findById(dto.getFranquiciaId())
                .orElseThrow(() -> new IllegalArgumentException("Franquicia no encontrada."));

        entity.setFranquicia(franquicia);
        entity.setNombreBd(dto.getNombreBd());
        entity.setTipoBd(dto.getTipoBd());
        entity.setHost(dto.getHost());
        entity.setPort(dto.getPort());
        entity.setDriver(dto.getDriver());
        entity.setUsuarioConexion(dto.getUsuarioConexion());
        entity.setPassConexionEncrypted(dto.getPassConexion());
        entity.setUrlConexion(dto.getUrlConexion());
        entity.setUpdatedBy(dto.getCreatedBy());
        entity.setUpdatedAt(Timestamp.from(Instant.now()));

        repository.save(entity);

        return toDTO(entity);
    }

    @Override
    public BaseDatosFranquiciaResponseDTO obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Base de datos no encontrada."));
    }

    @Override
    public List<BaseDatosFranquiciaResponseDTO> listarPorFranquicia(Integer franquiciaId) {
        return repository.findByFranquiciaId(franquiciaId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public void eliminar(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Registro BD no encontrado.");
        }
        repository.deleteById(id);
    }

    @Override
    public BaseDatosFranquiciaResponseDTO cambiarEstado(Integer id, String nuevoEstado) {
        BaseDatosFranquicia entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registro BD no encontrado."));

        entity.setEstado(EstadoBaseDatos.valueOf(nuevoEstado.toUpperCase()));
        entity.setUpdatedAt(Timestamp.from(Instant.now()));

        repository.save(entity);

        return toDTO(entity);
    }

    // ----------------------------------------------------
    // MAPPER INTERNO
    // ----------------------------------------------------

    private BaseDatosFranquicia toEntity(BaseDatosFranquiciaRequestDTO dto, Franquicia franquicia) {
        return BaseDatosFranquicia.builder()
                .franquicia(franquicia)
                .nombreBd(dto.getNombreBd())
                .tipoBd(dto.getTipoBd())
                .host(dto.getHost())
                .port(dto.getPort())
                .driver(dto.getDriver())
                .usuarioConexion(dto.getUsuarioConexion())
                .passConexionEncrypted(dto.getPassConexion()) // <-- Aquí podrías encriptarlo luego
                .urlConexion(dto.getUrlConexion())
                .createdBy(dto.getCreatedBy())
                .createdAt(Timestamp.from(Instant.now()))
                .estado(dto.getEstado())
                .build();
    }

    private BaseDatosFranquiciaResponseDTO toDTO(BaseDatosFranquicia entity) {
        return BaseDatosFranquiciaResponseDTO.builder()
                .id(entity.getId())
                .franquiciaId(entity.getFranquicia().getId())
                .nombreBd(entity.getNombreBd())
                .tipoBd(entity.getTipoBd())
                .estado(entity.getEstado())
                .host(entity.getHost())
                .port(entity.getPort())
                .driver(entity.getDriver())
                .usuarioConexion(entity.getUsuarioConexion())
                .passConexion(entity.getPassConexionEncrypted())
                .urlConexion(entity.getUrlConexion())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
