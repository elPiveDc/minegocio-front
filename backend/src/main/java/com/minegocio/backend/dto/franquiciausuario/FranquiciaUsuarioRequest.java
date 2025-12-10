package com.minegocio.backend.dto.franquiciausuario;

import com.minegocio.backend.model.enums.FranquiciaUsuarioRol;
import com.minegocio.backend.model.enums.FranquiciaUsuarioEstado;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FranquiciaUsuarioRequest {

    @NotNull(message = "El ID de la franquicia no puede ser nulo")
    private Integer idFranquicia;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Integer idUsuario;

    @NotNull(message = "El rol no puede ser nulo")
    private FranquiciaUsuarioRol rol;

    @NotNull(message = "El estado no puede ser nulo")
    private FranquiciaUsuarioEstado estado;

    // La fecha de asignación y el ID propio no se incluyen en el request
    // ya que se suelen generar en el servicio/entidad.
}