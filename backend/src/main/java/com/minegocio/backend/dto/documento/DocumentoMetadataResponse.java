package com.minegocio.backend.dto.documento;

import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Builder
public class DocumentoMetadataResponse {

    private Long id;
    private String titulo;
    private String slug;
    private String tipoContenido;
    private Timestamp fechaSubida;
}