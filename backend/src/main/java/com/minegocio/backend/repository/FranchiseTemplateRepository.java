package com.minegocio.backend.repository;

import com.minegocio.backend.model.FranchiseTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FranchiseTemplateRepository extends JpaRepository<FranchiseTemplate, Integer> {

    List<FranchiseTemplate> findByFranquiciaId(Integer idFranquicia);
}
