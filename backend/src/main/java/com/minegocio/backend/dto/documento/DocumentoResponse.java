package com.minegocio.backend.dto.documento;

import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Builder
public class DocumentoResponse {

    private Long id;
    private String titulo;
    private String slug;
    private String tipoContenido;
    private byte[] archivo; // Incluido para la descarga/visualización
    private Timestamp fechaSubida;
}