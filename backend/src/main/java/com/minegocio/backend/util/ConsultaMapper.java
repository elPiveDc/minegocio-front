package com.minegocio.backend.util;

import com.minegocio.backend.dto.consulta.*;
import com.minegocio.backend.model.Consulta;
import com.minegocio.backend.model.Usuario;

public class ConsultaMapper {

    public static ConsultaResponseDto toDto(Consulta c) {
        return ConsultaResponseDto.builder()
                .id(c.getId())
                .idUsuario(c.getUsuario().getId())
                .tipoConsulta(c.getTipoConsulta())
                .descripcion(c.getDescripcion())
                .respuesta(c.getRespuesta())
                .fechaCreacion(c.getFechaCreacion())
                .estado(c.getEstado())
                .build();
    }

    public static Consulta fromCreateDto(ConsultaCreateDto dto, Usuario usuario) {
        return Consulta.builder()
                .usuario(usuario)
                .tipoConsulta(dto.getTipoConsulta())
                .descripcion(dto.getDescripcion())
                .build();
    }
}
