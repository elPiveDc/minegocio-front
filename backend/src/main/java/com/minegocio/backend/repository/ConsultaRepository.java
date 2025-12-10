package com.minegocio.backend.repository;

import com.minegocio.backend.model.Consulta;
import com.minegocio.backend.model.Usuario;
import com.minegocio.backend.model.enums.EstadoConsulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

    List<Consulta> findByUsuario(Usuario usuario);

    List<Consulta> findByEstado(EstadoConsulta estado);
}
