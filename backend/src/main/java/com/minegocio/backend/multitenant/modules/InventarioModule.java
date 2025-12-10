package com.minegocio.backend.multitenant.modules;

public class InventarioModule implements Modulos {

    @Override
    public String getNombre() {
        return "INVENTARIO";
    }

    @Override
    public String generarDDL(String tabla) {
        return """
                CREATE TABLE %s (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(255),
                    stock INT,
                    precio DECIMAL(10,2)
                );
                """.formatted(tabla);
    }
}
