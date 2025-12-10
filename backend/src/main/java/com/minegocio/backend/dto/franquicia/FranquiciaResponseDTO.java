package com.minegocio.backend.dto.franquicia;

import com.minegocio.backend.model.enums.EstadoFranquicia;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranquiciaResponseDTO {

    private Integer id;
    private String nombreFranquicia;
    private String slug;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private EstadoFranquicia estado;
    private Integer templateId;
}
