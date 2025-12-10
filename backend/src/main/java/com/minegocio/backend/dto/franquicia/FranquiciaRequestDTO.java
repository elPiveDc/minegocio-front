package com.minegocio.backend.dto.franquicia;

import jakarta.validation.constraints.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranquiciaRequestDTO {

    @NotBlank
    @Size(max = 150)
    private String nombreFranquicia;

    @NotBlank
    @Size(max = 160)
    private String slug;

    @Size(max = 10_000)
    private String descripcion;

    private Integer templateId;

}
