package com.minegocio.backend.service;

import com.minegocio.backend.dto.basedatos.*;

import java.util.List;

public interface BaseDatosFranquiciaService {

    BaseDatosFranquiciaResponseDTO crear(BaseDatosFranquiciaRequestDTO dto);

    BaseDatosFranquiciaResponseDTO actualizar(Integer id, BaseDatosFranquiciaRequestDTO dto);

    BaseDatosFranquiciaResponseDTO obtenerPorId(Integer id);

    List<BaseDatosFranquiciaResponseDTO> listarPorFranquicia(Integer franquiciaId);

    void eliminar(Integer id);

    BaseDatosFranquiciaResponseDTO cambiarEstado(Integer id, String nuevoEstado);
}
