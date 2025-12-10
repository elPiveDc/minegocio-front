package com.minegocio.backend.service;

import com.minegocio.backend.dto.franquicia.FranquiciaRequestDTO;
import com.minegocio.backend.dto.franquiciausuario.FranquiciaUsuarioRequest;
import com.minegocio.backend.dto.basedatos.BaseDatosFranquiciaRequestDTO;
import com.minegocio.backend.dto.franquiciamodulo.FranquiciaModuloRequestDTO;
import com.minegocio.backend.dto.objetobdfranquicia.ObjetoBdFranquiciaRequest;

import com.minegocio.backend.model.Usuario;
import com.minegocio.backend.model.enums.EstadoBaseDatos;
import com.minegocio.backend.model.enums.FranquiciaUsuarioEstado;
import com.minegocio.backend.model.enums.FranquiciaUsuarioRol;
import com.minegocio.backend.model.enums.TipoBaseDatos;
import com.minegocio.backend.model.enums.TipoObjeto;
import com.minegocio.backend.multitenant.core.TenantCredentials;
import com.minegocio.backend.multitenant.services.TenantService;
import com.minegocio.backend.model.Modulo;

import com.minegocio.backend.repository.UsuarioRepository;
import com.minegocio.backend.repository.ModuloRepository;

import com.minegocio.backend.util.DatabaseProvisioningUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FranquiciaOrquestadorService {

        private final UsuarioRepository usuarioRepository;
        private final ModuloRepository moduloRepository;

        private final FranquiciaService franquiciaService;
        private final FranquiciaUsuarioService franquiciaUsuarioService;
        private final BaseDatosFranquiciaService baseDatosFranquiciaService;
        private final FranquiciaModuloService franquiciaModulosService;
        private final ObjetoBdFranquiciaService objetoBdFranquiciaService;

        private final DatabaseProvisioningUtil dbUtil;

        private final TenantService tenantService;

        @Transactional
        public void registrarFranquiciaCompleta(FranquiciaRequestDTO dto) {
                try {
                        log.info("=== Iniciando proceso completo de creación de franquicia ===");

                        // -------------------------------------------------------------
                        // 1. Obtener usuario autenticado (correo desde JWT)
                        // -------------------------------------------------------------
                        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                        String correo = auth.getName();

                        Usuario usuario = usuarioRepository.findByCorreo(correo)
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Usuario autenticado no encontrado en BD central"));

                        log.info("Usuario creador encontrado: {}", usuario.getCorreo());

                        // -------------------------------------------------------------
                        // 2. Crear franquicia en BD central
                        // -------------------------------------------------------------
                        var franquiciaResponse = franquiciaService.crear(dto);
                        Integer franquiciaId = franquiciaResponse.getId();

                        log.info("Franquicia creada con ID: {}", franquiciaId);

                        // -------------------------------------------------------------
                        // 3. Asignar usuario creador a la franquicia y Crear tabla usuarios e insertar
                        // usuario creador
                        // -------------------------------------------------------------
                        FranquiciaUsuarioRequest reqUsuarioFra = FranquiciaUsuarioRequest.builder()
                                        .idFranquicia(franquiciaId)
                                        .idUsuario(usuario.getId())
                                        .rol(FranquiciaUsuarioRol.OWNER)
                                        .estado(FranquiciaUsuarioEstado.ACEPTADO)
                                        .build();

                        franquiciaUsuarioService.crear(reqUsuarioFra);

                        log.info("Usuario {} asignado a franquicia {}", usuario.getCorreo(), franquiciaId);

                        TenantCredentials creds = tenantService.createTenant(
                                        franquiciaResponse.getSlug(),
                                        dbUtil.generarPasswordSegura(),
                                        usuario.getNombre(),
                                        usuario.getCorreo(),
                                        "OWNER");
                        String bdname = franquiciaResponse.getSlug().replace("-", "_");

                        log.info("Base de datos real creada y configurada: {}", bdname);

                        // -------------------------------------------------------------
                        // 5. Registrar datos de BD real en BD central
                        // -------------------------------------------------------------
                        BaseDatosFranquiciaRequestDTO reqBd = BaseDatosFranquiciaRequestDTO.builder()
                                        .franquiciaId(franquiciaId)
                                        .nombreBd(bdname)
                                        .usuarioConexion(creds.getUsername())
                                        .passConexion(creds.getPassword())
                                        .urlConexion(creds.getUrl())
                                        .driver("com.mysql.cj.jdbc.Driver")
                                        .host("localhost")
                                        .port(3306)
                                        .estado(EstadoBaseDatos.CONFIGURADA)
                                        .tipoBd(TipoBaseDatos.MYSQL)
                                        .createdBy(usuario.getId())
                                        .build();

                        var bdResponse = baseDatosFranquiciaService.crear(reqBd);
                        Integer bdId = bdResponse.getId();

                        log.info("Registro de BD central creado con ID: {}", bdId);

                        log.info("Tabla usuarios creada en BD real y usuario OWNER insertado.");

                        // -------------------------------------------------------------
                        // 7. Asignar módulo GESTION_USUARIOS
                        // -------------------------------------------------------------
                        String estructura = dbUtil.generarJsonEstructuraTablaUsuarios();

                        Modulo modulo = moduloRepository.findByClave("GESTION_USUARIOS")
                                        .orElseThrow(() -> new RuntimeException(
                                                        "No existe el módulo GESTION_USUARIOS"));

                        FranquiciaModuloRequestDTO reqModulo = FranquiciaModuloRequestDTO.builder()
                                        .franquiciaId(franquiciaId)
                                        .moduloId(modulo.getId())
                                        .activo(true)
                                        .configuracion(estructura)
                                        .build();

                        franquiciaModulosService.asignarModulo(reqModulo);

                        log.info("Módulo GESTION_USUARIOS asignado a la franquicia.");

                        // -------------------------------------------------------------
                        // 8. Registrar la tabla creada en objeto_bd_franquicia
                        // -------------------------------------------------------------

                        ObjetoBdFranquiciaRequest reqObjeto = ObjetoBdFranquiciaRequest.builder()
                                        .idBaseDatos(bdId)
                                        .nombreTabla("usuarios")
                                        .columnas(estructura)
                                        .createdBy(usuario.getId())
                                        .idModulo(1)
                                        .tipoObjeto(TipoObjeto.TABLA)
                                        .esTablaUsuarios(true)
                                        .editable(true)
                                        .build();

                        objetoBdFranquiciaService.crear(reqObjeto);

                        log.info("Tabla usuarios registrada en objeto_bd_franquicia.");

                        // -------------------------------------------------------------
                        log.info("=== PROCESO COMPLETO DE CREACIÓN DE FRANQUICIA FINALIZADO ===");

                } catch (Exception e) {
                        log.error("Error durante la creación completa de franquicia", e);
                        throw new RuntimeException("Error en el proceso de creación de franquicia: " + e.getMessage(),
                                        e);
                }
        }
}
