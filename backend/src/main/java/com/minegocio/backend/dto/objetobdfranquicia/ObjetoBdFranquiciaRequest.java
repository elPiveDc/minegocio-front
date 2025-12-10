package com.minegocio.backend.dto.objetobdfranquicia;

import com.minegocio.backend.model.enums.TipoObjeto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ObjetoBdFranquiciaRequest {

    @NotNull(message = "El ID de la Base de Datos es obligatorio")
    private Integer idBaseDatos; // Relación BaseDatosFranquicia

    private Integer idModulo; // Relación Modulo (opcional)

    @NotBlank(message = "El nombre de la tabla no puede estar vacío")
    @Size(max = 150, message = "El nombre de la tabla no puede exceder los 150 caracteres")
    private String nombreTabla;

    @NotNull(message = "El tipo de objeto es obligatorio")
    private TipoObjeto tipoObjeto;

    @NotNull(message = "Debe especificar si es tabla de usuarios")
    private Boolean esTablaUsuarios;

    @NotBlank(message = "Las columnas (JSON) son obligatorias")
    private String columnas; // Campo JSON como String

    @NotNull(message = "Debe especificar si es editable")
    private Boolean editable;

    private Integer createdBy; // Para auditoría (quién lo crea)
    private Integer updatedBy; // Para auditoría (quién lo actualiza)
}