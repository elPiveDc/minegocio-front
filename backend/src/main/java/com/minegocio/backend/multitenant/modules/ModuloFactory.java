package com.minegocio.backend.multitenant.modules;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ModuloFactory {

    private final Map<String, Modulos> modulos = Map.of(
            "INVENTARIO", new InventarioModule()
    // agregar más módulos aquí
    );

    public Modulos get(String clave) {
        return modulos.get(clave);
    }
}
