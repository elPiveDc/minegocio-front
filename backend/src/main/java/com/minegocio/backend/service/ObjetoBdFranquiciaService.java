package com.minegocio.backend.service;

import com.minegocio.backend.dto.objetobdfranquicia.ObjetoBdFranquiciaRequest;
import com.minegocio.backend.dto.objetobdfranquicia.ObjetoBdFranquiciaResponse;

import java.util.List;

public interface ObjetoBdFranquiciaService {

    // Operaciones CRUD
    ObjetoBdFranquiciaResponse crear(ObjetoBdFranquiciaRequest request);

    ObjetoBdFranquiciaResponse obtenerPorId(Integer id);

    ObjetoBdFranquiciaResponse actualizar(Integer id, ObjetoBdFranquiciaRequest request);

    void eliminar(Integer id);

    List<ObjetoBdFranquiciaResponse> obtenerTodos();

    // Operaciones específicas del repositorio
    List<ObjetoBdFranquiciaResponse> obtenerPorBaseDatosId(Integer idBd);

    boolean existePorNombreTablaYBaseDatosId(String nombreTabla, Integer idBd);
}