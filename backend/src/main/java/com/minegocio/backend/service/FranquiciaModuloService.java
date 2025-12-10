package com.minegocio.backend.service;

import com.minegocio.backend.dto.franquiciamodulo.*;

import java.util.List;

public interface FranquiciaModuloService {

    FranquiciaModuloResponseDTO asignarModulo(FranquiciaModuloRequestDTO dto);

    FranquiciaModuloResponseDTO actualizar(Integer id, FranquiciaModuloRequestDTO dto);

    FranquiciaModuloResponseDTO obtenerPorId(Integer id);

    List<FranquiciaModuloResponseDTO> listarPorFranquicia(Integer franquiciaId);

    FranquiciaModuloResponseDTO obtenerPorFranquiciaYModulo(Integer franquiciaId, Integer moduloId);

    void eliminar(Integer id);
}
