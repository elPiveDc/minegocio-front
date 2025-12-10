package com.minegocio.backend.repository;

import com.minegocio.backend.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    Optional<Documento> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Documento> findByTituloContainingIgnoreCase(String titulo);

}
