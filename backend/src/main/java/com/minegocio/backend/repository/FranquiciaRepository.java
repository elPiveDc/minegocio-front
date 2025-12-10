package com.minegocio.backend.repository;

import com.minegocio.backend.model.Franquicia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FranquiciaRepository extends JpaRepository<Franquicia, Integer> {

    boolean existsByNombreFranquicia(String nombreFranquicia);

    boolean existsBySlug(String slug);

    @Query("SELECT fu.franquicia FROM FranquiciaUsuario fu WHERE fu.usuario.id = :idUsuario")
    List<Franquicia> findFranquiciasByUsuarioId(Integer idUsuario);
}
