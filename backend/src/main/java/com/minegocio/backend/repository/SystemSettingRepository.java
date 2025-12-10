package com.minegocio.backend.repository;

import com.minegocio.backend.model.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemSettingRepository extends JpaRepository<SystemSetting, Integer> {

    Optional<SystemSetting> findByClave(String clave);
}
