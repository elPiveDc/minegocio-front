package com.minegocio.backend.service;

public interface ModuloInstalacionService {
    void instalarModulo(Integer idFranquicia, Integer idUsuario, String claveModulo, String nombreTabla);
}
