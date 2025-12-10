package com.minegocio.backend.service;

import com.minegocio.backend.dto.franquicia.FranquiciaResponseDTO;
import com.minegocio.backend.dto.franquiciausuario.FranquiciaUsuarioRequest;
import com.minegocio.backend.dto.franquiciausuario.FranquiciaUsuarioResponse;
import com.minegocio.backend.model.Franquicia;
import com.minegocio.backend.model.enums.FranquiciaUsuarioRol;

import java.util.List;

public interface FranquiciaUsuarioService {

    // Operaciones CRUD
    FranquiciaUsuarioResponse crear(FranquiciaUsuarioRequest request);

    FranquiciaUsuarioResponse obtenerPorId(Integer id);

    FranquiciaUsuarioResponse actualizar(Integer id, FranquiciaUsuarioRequest request);

    void eliminar(Integer id);

    List<FranquiciaUsuarioResponse> obtenerTodos();

    // Operaciones específicas del repositorio
    List<FranquiciaUsuarioResponse> obtenerPorFranquiciaId(Integer idFranquicia);

    List<FranquiciaUsuarioResponse> obtenerPorUsuarioId(Integer idUsuario);

    List<FranquiciaResponseDTO> listarFranquiciasPorUsuario(Integer idUsuario);

    boolean existePorFranquiciaIdYUsuarioId(Integer idFranquicia, Integer idUsuario);

    FranquiciaUsuarioResponse obtenerPorFranquiciaIdYUsuarioId(Integer idFranquicia, Integer idUsuario);

    List<FranquiciaUsuarioResponse> obtenerPorFranquiciaIdYRol(Integer idFranquicia, FranquiciaUsuarioRol rol);
}