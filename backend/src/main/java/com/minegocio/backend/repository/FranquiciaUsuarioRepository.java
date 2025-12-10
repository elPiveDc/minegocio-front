package com.minegocio.backend.repository;

import com.minegocio.backend.model.FranquiciaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FranquiciaUsuarioRepository extends JpaRepository<FranquiciaUsuario, Integer> {

    List<FranquiciaUsuario> findByFranquiciaId(Integer idFranquicia);

    List<FranquiciaUsuario> findByUsuarioId(Integer idUsuario);

    boolean existsByFranquiciaIdAndUsuarioId(Integer idFranquicia, Integer idUsuario);

    Optional<FranquiciaUsuario> findByFranquiciaIdAndUsuarioId(Integer idFranquicia, Integer idUsuario);

    List<FranquiciaUsuario> findByFranquiciaIdAndRol(Integer idFranquicia, String rol);

}
