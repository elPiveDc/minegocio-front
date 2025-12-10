package com.minegocio.backend.dto.franquiciamodulo;

import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranquiciaModuloResponseDTO {

    private Integer id;
    private Integer franquiciaId;
    private Integer moduloId;

    private Boolean activo;
    private String configuracion;

    private Timestamp fechaInstalacion;
}
