package com.minegocio.backend.multitenant.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.minegocio.backend.multitenant.core.TenantCredentials;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

    private final JdbcTemplate jdbcAdmin;

    @Value("${multitenant.mysql.host}")
    private String host;

    @Value("${multitenant.mysql.port}")
    private String port;

    public TenantCredentials createTenant(
            String slug,
            String password,
            String nombreCreador,
            String correo,
            String rol) throws Exception {

        String dbName = slug.replace("-", "_");

        log.info("Creando base de datos física: {}", dbName);

        jdbcAdmin.execute("CREATE DATABASE IF NOT EXISTS `" + dbName + "`");
        jdbcAdmin.execute("CREATE USER IF NOT EXISTS '" + slug + "'@'%' IDENTIFIED BY '" + password + "'");
        jdbcAdmin.execute("GRANT ALL PRIVILEGES ON `" + dbName + "`.* TO '" + slug + "'@'%'");
        jdbcAdmin.execute("FLUSH PRIVILEGES");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false&allowPublicKeyRetrieval=true";

        return new TenantCredentials(url, slug, password);
    }
}
