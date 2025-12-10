package com.minegocio.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minegocio.backend.model.ObjetoBdFranquicia;

public interface ObjetoBdFranquiciaRepository extends JpaRepository<ObjetoBdFranquicia, Integer> {

    List<ObjetoBdFranquicia> findByBaseDatosFranquiciaId(Integer idBd);

    boolean existsByNombreTablaAndBaseDatosFranquiciaId(String nombreTabla, Integer idBd);
}