package com.minegocio.backend.service;

import com.minegocio.backend.dto.modulo.ModuloRequest;
import com.minegocio.backend.dto.modulo.ModuloResponse;

import java.util.List;

public interface ModuloService {

    // Operaciones CRUD
    ModuloResponse crear(ModuloRequest request);

    ModuloResponse obtenerPorId(Integer id);

    ModuloResponse actualizar(Integer id, ModuloRequest request);

    void eliminar(Integer id);

    List<ModuloResponse> obtenerTodos();

    // Operaciones específicas del repositorio
    ModuloResponse obtenerPorClave(String clave);
}