package com.minegocio.backend.service;

import com.minegocio.backend.dto.franquicia.*;

import java.util.List;

public interface FranquiciaService {

    FranquiciaResponseDTO crear(FranquiciaRequestDTO dto);

    FranquiciaResponseDTO actualizar(Integer id, FranquiciaRequestDTO dto);

    FranquiciaResponseDTO obtenerPorId(Integer id);

    FranquiciaResponseDTO obtenerPorSlug(String slug);

    List<FranquiciaResponseDTO> listar();

    void eliminar(Integer id);

    boolean existeNombre(String nombreFranquicia);

    boolean existeSlug(String slug);

    FranquiciaResponseDTO cambiarEstado(Integer id, String nuevoEstado);
}
