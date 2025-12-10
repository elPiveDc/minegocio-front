package com.minegocio.backend.repository;

import com.minegocio.backend.model.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuloRepository extends JpaRepository<Modulo, Integer> {

    Optional<Modulo> findByClave(String clave);
}
