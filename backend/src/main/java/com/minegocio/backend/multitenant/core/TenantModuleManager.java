package com.minegocio.backend.multitenant.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.minegocio.backend.multitenant.modules.Modulos;

@Component
@Slf4j
public class TenantModuleManager {

    @Getter
    private String lastSql = "";

    public void addModuleToTenant(
            String url,
            String username,
            String password,
            Modulos modulo,
            String nombreTabla) throws Exception {

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);

        JdbcTemplate tenantJdbc = new JdbcTemplate(ds);

        String ddl = modulo.generarDDL(nombreTabla);
        this.lastSql = ddl;

        log.info("Ejecutando DDL en tenant: {}", ddl);
        tenantJdbc.execute(ddl);
    }

    public String getsql() {
        return lastSql;
    }
}
