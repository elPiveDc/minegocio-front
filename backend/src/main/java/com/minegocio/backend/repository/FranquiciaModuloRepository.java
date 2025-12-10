package com.minegocio.backend.repository;

import com.minegocio.backend.model.FranquiciaModulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FranquiciaModuloRepository extends JpaRepository<FranquiciaModulo, Integer> {

    List<FranquiciaModulo> findByFranquiciaId(Integer idFranquicia);

    Optional<FranquiciaModulo> findByFranquiciaIdAndModuloId(Integer idFranquicia, Integer idModulo);

    boolean existsByFranquiciaIdAndModuloId(Integer idFranquicia, Integer idModulo);
}
