package com.minegocio.backend.dto.basedatos;

import com.minegocio.backend.model.enums.EstadoBaseDatos;
import com.minegocio.backend.model.enums.TipoBaseDatos;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseDatosFranquiciaRequestDTO {

    @NotNull
    private Integer franquiciaId;

    @NotBlank
    @Size(max = 150)
    private String nombreBd;

    @NotNull
    private TipoBaseDatos tipoBd;

    @Size(max = 255)
    private String host;

    private Integer port;

    @Size(max = 100)
    private String driver;

    @Size(max = 150)
    private String usuarioConexion;

    @Size(max = 512)
    private String passConexion;

    private String urlConexion;

    private EstadoBaseDatos estado;

    @NotNull
    private Integer createdBy;
}
