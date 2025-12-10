package com.minegocio.backend.multitenant.modules;

public interface Modulos {
    String getNombre();

    String generarDDL(String nombreTabla);
}
