package com.minegocio.backend.service.impl;

import com.minegocio.backend.dto.consulta.*;
import com.minegocio.backend.util.ConsultaMapper;
import com.minegocio.backend.model.Consulta;
import com.minegocio.backend.model.Usuario;
import com.minegocio.backend.model.enums.EstadoConsulta;
import com.minegocio.backend.repository.ConsultaRepository;
import com.minegocio.backend.repository.UsuarioRepository;
import com.minegocio.backend.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public ConsultaResponseDto crearConsulta(ConsultaCreateDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Consulta consulta = ConsultaMapper.fromCreateDto(dto, usuario);
        consultaRepository.save(consulta);

        return ConsultaMapper.toDto(consulta);
    }

    @Override
    public ConsultaResponseDto obtenerPorId(Integer id) {
        return consultaRepository.findById(id)
                .map(ConsultaMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));
    }

    @Override
    public List<ConsultaResponseDto> obtenerPorUsuario(Integer idUsuario) {
        Usuario u = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return consultaRepository.findByUsuario(u)
                .stream()
                .map(ConsultaMapper::toDto)
                .toList();
    }

    @Override
    public List<ConsultaResponseDto> obtenerPorEstado(String estado) {
        EstadoConsulta est = EstadoConsulta.valueOf(estado.toUpperCase());

        return consultaRepository.findByEstado(est)
                .stream()
                .map(ConsultaMapper::toDto)
                .toList();
    }

    @Override
    public List<ConsultaResponseDto> obtenerTodos() {
        return consultaRepository.findAll()
                .stream()
                .map(ConsultaMapper::toDto)
                .toList();
    }

    @Override
    public ConsultaResponseDto actualizarConsulta(Integer id, ConsultaUpdateDto dto) {
        Consulta c = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));

        c.setTipoConsulta(dto.getTipoConsulta());
        c.setDescripcion(dto.getDescripcion());

        return ConsultaMapper.toDto(consultaRepository.save(c));
    }

    @Override
    public ConsultaResponseDto cambiarEstado(Integer id, ConsultaEstadoDto dto) {
        Consulta c = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));

        c.setEstado(dto.getEstado());

        return ConsultaMapper.toDto(consultaRepository.save(c));
    }

    @Override
    public ConsultaResponseDto responderConsulta(Integer id, ConsultaRespuestaDto dto) {
        Consulta c = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));

        c.setRespuesta(dto.getRespuesta());
        c.setEstado(EstadoConsulta.RESUELTO);

        return ConsultaMapper.toDto(consultaRepository.save(c));
    }

    @Override
    public void eliminarConsulta(Integer id) {
        if (!consultaRepository.existsById(id))
            throw new RuntimeException("Consulta no encontrada");

        consultaRepository.deleteById(id);
    }
}
