package com.minegocio.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minegocio.backend.dto.objetobdfranquicia.ObjetoBdFranquiciaRequest;
import com.minegocio.backend.model.enums.FranquiciaUsuarioRol;
import com.minegocio.backend.model.enums.TipoObjeto;
import com.minegocio.backend.multitenant.core.TenantModuleManager;
import com.minegocio.backend.multitenant.modules.ModuloFactory;
import com.minegocio.backend.multitenant.modules.Modulos;
import com.minegocio.backend.service.BaseDatosFranquiciaService;
import com.minegocio.backend.service.FranquiciaUsuarioService;
import com.minegocio.backend.service.ModuloInstalacionService;
import com.minegocio.backend.service.ModuloService;
import com.minegocio.backend.service.ObjetoBdFranquiciaService;
import com.minegocio.backend.util.DatabaseProvisioningUtil;

@Service
public class ModuloInstalacionServiceImpl implements ModuloInstalacionService {

        @Autowired
        private FranquiciaUsuarioService franquiciaUsuarioService;

        @Autowired
        private ModuloService moduloService;

        @Autowired
        private BaseDatosFranquiciaService baseDatosFranquiciaService;

        @Autowired
        private ObjetoBdFranquiciaService objetoBdFranquiciaService;

        private ModuloFactory moduloFactory;

        private DatabaseProvisioningUtil bbp;

        private TenantModuleManager tenantModuleManager;

        @Override
        public void instalarModulo(Integer idFranquicia, Integer idUsuario, String claveModulo, String nombreTabla) {

                // Verificar rol del usuario en la franquicia
                var fu = franquiciaUsuarioService
                                .obtenerPorFranquiciaIdYUsuarioId(idFranquicia, idUsuario);

                if (!tienePermisos(fu.getRol())) {
                        throw new IllegalStateException("No tiene permisos para instalar módulos");
                }

                // Verificar que el módulo existe
                var moduloCatalogo = moduloService.obtenerPorClave(claveModulo);

                // Buscar la BD principal de la franquicia
                var listaBd = baseDatosFranquiciaService.listarPorFranquicia(idFranquicia);
                var bd = listaBd.stream()
                                .filter(b -> b.getEstado().equals("CONFIGURADA"))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("La franquicia no tiene BD configurada"));

                // Verificar que la tabla no esté instalada ya
                if (objetoBdFranquiciaService.existePorNombreTablaYBaseDatosId(
                                moduloCatalogo.getNombre(),
                                bd.getId())) {

                        throw new IllegalStateException("El módulo ya está instalado");
                }

                // Obtener credenciales del tenant
                String url = bd.getUrlConexion();
                String user = bd.getUsuarioConexion();
                String pass = bd.getPassConexion(); // o bd.getPassConexionEncrypted() + decrypt

                // Construir el módulo
                Modulos module = moduloFactory.get(moduloCatalogo.getClave());

                // Crear tablas en el tenant
                try {
                        tenantModuleManager.addModuleToTenant(
                                        url, user, pass, module, moduloCatalogo.getNombre());
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

                String json = bbp.generarJsonEstructuraTablaUsuarios();

                // Registrar que la tabla fue creada
                objetoBdFranquiciaService.crear(
                                ObjetoBdFranquiciaRequest.builder()
                                                .idBaseDatos(bd.getId())
                                                .nombreTabla(nombreTabla)
                                                .tipoObjeto(TipoObjeto.TABLA)
                                                .columnas(json)
                                                .editable(true)
                                                .build());
        }

        private boolean tienePermisos(FranquiciaUsuarioRol rol) {
                return rol == FranquiciaUsuarioRol.OWNER
                                || rol == FranquiciaUsuarioRol.CO_OWNER
                                || rol == FranquiciaUsuarioRol.ADMIN;
        }
}
