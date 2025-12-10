package com.minegocio.backend.dto.faq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FaqRequest {

    @NotBlank(message = "La pregunta no puede estar vacía")
    @Size(max = 500, message = "La pregunta no puede exceder los 500 caracteres")
    private String question;

    @NotBlank(message = "La respuesta no puede estar vacía")
    private String answer;

    // El ID y las marcas de tiempo se manejan internamente.
}
