package com.minegocio.backend.service;

import com.minegocio.backend.dto.consulta.*;

import java.util.List;

public interface ConsultaService {

    ConsultaResponseDto crearConsulta(ConsultaCreateDto dto);

    ConsultaResponseDto obtenerPorId(Integer id);

    List<ConsultaResponseDto> obtenerPorUsuario(Integer idUsuario);

    List<ConsultaResponseDto> obtenerPorEstado(String estado);

    List<ConsultaResponseDto> obtenerTodos();

    ConsultaResponseDto actualizarConsulta(Integer id, ConsultaUpdateDto dto);

    ConsultaResponseDto cambiarEstado(Integer id, ConsultaEstadoDto dto);

    ConsultaResponseDto responderConsulta(Integer id, ConsultaRespuestaDto dto);

    void eliminarConsulta(Integer id);

}
