package com.minegocio.backend.repository;

import com.minegocio.backend.model.BaseDatosFranquicia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseDatosFranquiciaRepository extends JpaRepository<BaseDatosFranquicia, Integer> {

    List<BaseDatosFranquicia> findByFranquiciaId(Integer idFranquicia);
}
