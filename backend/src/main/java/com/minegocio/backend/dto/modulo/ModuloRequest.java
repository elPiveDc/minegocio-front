package com.minegocio.backend.dto.modulo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModuloRequest {

    @NotBlank(message = "La clave no puede estar vacía")
    @Size(max = 100, message = "La clave no puede exceder los 100 caracteres")
    private String clave;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 150, message = "El nombre no puede exceder los 150 caracteres")
    private String nombre;

    private String descripcion;

    // El ID y createdAt se manejan internamente, no se incluyen en el Request.
}