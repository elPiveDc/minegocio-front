package com.minegocio.backend.dto.documento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentoRequest {

    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 255, message = "El título no puede exceder los 255 caracteres")
    private String titulo;

    @NotBlank(message = "El slug no puede estar vacío")
    @Size(max = 150, message = "El slug no puede exceder los 150 caracteres")
    private String slug;

    @NotBlank(message = "El tipo de contenido (MIME Type) es obligatorio")
    @Size(max = 100, message = "El tipo de contenido no puede exceder los 100 caracteres")
    private String tipoContenido;

    @NotNull(message = "El archivo es obligatorio")
    private byte[] archivo; // Contenido binario del archivo
}