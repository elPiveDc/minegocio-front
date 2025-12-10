package com.minegocio.backend.multitenant.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.minegocio.backend.multitenant.core.TenantCredentials;
import com.minegocio.backend.multitenant.core.TenantModuleManager;
import com.minegocio.backend.multitenant.modules.ModuloFactory;
import com.minegocio.backend.multitenant.modules.Modulos;

@Service
@RequiredArgsConstructor
@Slf4j
public class MultitenantOrquestadorService {

    private final TenantService tenantService;
    private final ModuloFactory moduloFactory;
    private final TenantModuleManager tenantModuleManager;

    public TenantCredentials crearBaseTenant(
            String slug,
            String password,
            String nombre,
            String correo) throws Exception {

        return tenantService.createTenant(
                slug,
                password,
                nombre,
                correo,
                "OWNER");
    }

    public String aplicarModuloEnTenant(
            TenantCredentials creds,
            String claveModulo,
            String nombreTabla) throws Exception {

        Modulos modulo = moduloFactory.get(claveModulo);

        tenantModuleManager.addModuleToTenant(
                creds.getUrl(),
                creds.getUsername(),
                creds.getPassword(),
                modulo,
                nombreTabla);

        return tenantModuleManager.getsql();
    }
}
