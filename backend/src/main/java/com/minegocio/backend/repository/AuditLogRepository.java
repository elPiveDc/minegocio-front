package com.minegocio.backend.repository;

import com.minegocio.backend.model.AuditLog;
import com.minegocio.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByActor(Usuario usuario);
}
