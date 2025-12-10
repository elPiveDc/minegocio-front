package com.minegocio.backend.dto.franquiciamodulo;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranquiciaModuloRequestDTO {

    @NotNull
    private Integer franquiciaId;

    @NotNull
    private Integer moduloId;

    @NotNull
    private Boolean activo;

    private String configuracion; // JSON
}
