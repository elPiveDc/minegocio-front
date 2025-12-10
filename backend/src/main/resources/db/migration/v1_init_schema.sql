-- ============================================================
-- BASE DE DATOS CENTRAL - SISTEMA_FRANQUICIAS (MySQL 8+)
-- ============================================================

-- ============================================================
-- TABLA: usuarios
-- ============================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email_verificado BOOLEAN DEFAULT FALSE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('ACTIVO','INACTIVO','BLOQUEADO') DEFAULT 'ACTIVO',
    rol VARCHAR(50) NOT NULL DEFAULT 'USUARIO',
    avatar_url VARCHAR(512) NULL,
    zona_horaria VARCHAR(64) NULL,
    last_login TIMESTAMP NULL,
    INDEX idx_usuarios_correo (correo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: franquicias
-- ============================================================
CREATE TABLE IF NOT EXISTS franquicias (
    id_franquicia INT AUTO_INCREMENT PRIMARY KEY,
    nombre_franquicia VARCHAR(150) NOT NULL,
    slug VARCHAR(160) NOT NULL UNIQUE,
    descripcion TEXT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('ACTIVA','INACTIVA','ELIMINADA') DEFAULT 'ACTIVA',
    template_id INT NULL,
    INDEX idx_franquicias_slug (slug)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================================================
-- TABLA: franquicia_usuarios
-- ============================================================
CREATE TABLE IF NOT EXISTS franquicia_usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_franquicia INT NOT NULL,
    id_usuario INT NOT NULL,
    rol ENUM('OWNER','CO_OWNER','ADMIN','MANAGER','EMPLOYEE','VIEWER','INVITED') DEFAULT 'INVITED',
    estado ENUM('PENDIENTE','ACEPTADO','RECHAZADO') DEFAULT 'PENDIENTE',
    fecha_asignacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    UNIQUE KEY ux_franquicia_usuario (id_franquicia, id_usuario),

    CONSTRAINT fk_fu_franquicia FOREIGN KEY (id_franquicia)
        REFERENCES franquicias(id_franquicia)
        ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_fu_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE,

    INDEX idx_fu_frn (id_franquicia),
    INDEX idx_fu_usr (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: bases_datos_franquicia
-- ============================================================
CREATE TABLE IF NOT EXISTS bases_datos_franquicia (
    id_bd INT AUTO_INCREMENT PRIMARY KEY,
    id_franquicia INT NOT NULL,
    nombre_bd VARCHAR(150) NOT NULL,
    tipo_bd ENUM('MYSQL','POSTGRESQL','ORACLE','MONGODB','OTHER') DEFAULT 'MYSQL',
    estado ENUM('CONFIGURADA','NO_CONFIGURADA','ERROR') DEFAULT 'NO_CONFIGURADA',
    host VARCHAR(255) NULL,
    port INT NULL,
    driver VARCHAR(100) NULL,
    usuario_conexion VARCHAR(150) NULL,
    pass_conexion_encrypted VARCHAR(512) NULL,
    url_conexion TEXT NULL,
    created_by INT NULL,
    updated_by INT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_bd_frn FOREIGN KEY (id_franquicia)
        REFERENCES franquicias(id_franquicia)
        ON DELETE CASCADE ON UPDATE CASCADE,

    INDEX idx_bd_franquicia (id_franquicia),
    INDEX idx_bd_nombre (nombre_bd)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: modulos
-- ============================================================
CREATE TABLE IF NOT EXISTS modulos (
    id_modulo INT AUTO_INCREMENT PRIMARY KEY,
    clave VARCHAR(100) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;




-- ============================================================
-- TABLA: franquicia_modulos
-- ============================================================
CREATE TABLE IF NOT EXISTS franquicia_modulos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_franquicia INT NOT NULL,
    id_modulo INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    configuracion JSON NULL,
    fecha_instalacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_fm_frn FOREIGN KEY (id_franquicia)
        REFERENCES franquicias(id_franquicia)
        ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_fm_mod FOREIGN KEY (id_modulo)
        REFERENCES modulos(id_modulo)
        ON DELETE CASCADE ON UPDATE CASCADE,

    UNIQUE KEY ux_fm_frn_mod (id_franquicia, id_modulo),
    INDEX idx_fm_frn (id_franquicia)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: objetos_bd_franquicia
-- ============================================================
CREATE TABLE IF NOT EXISTS objetos_bd_franquicia (
    id_objeto INT AUTO_INCREMENT PRIMARY KEY,
    id_bd INT NOT NULL,
    nombre_tabla VARCHAR(150) NOT NULL,
    tipo_objeto ENUM('TABLA','VISTA','FUNCION','OTRO') DEFAULT 'TABLA',
    es_tabla_usuarios BOOLEAN DEFAULT FALSE,
    columnas JSON NOT NULL,
    id_modulo INT NULL,
    editable BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by INT NULL,
    updated_by INT NULL,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_obj_bd FOREIGN KEY (id_bd)
        REFERENCES bases_datos_franquicia(id_bd)
        ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_obj_mod FOREIGN KEY (id_modulo)
        REFERENCES modulos(id_modulo)
        ON DELETE SET NULL ON UPDATE CASCADE,

    INDEX idx_obj_bd (id_bd),
    INDEX idx_obj_tabla (nombre_tabla)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: invitations
-- ============================================================
CREATE TABLE IF NOT EXISTS invitations (
    id_invitation BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_franquicia INT NOT NULL,
    invited_email VARCHAR(255) NULL,
    invited_by INT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    role_offered ENUM('OWNER','CO_OWNER','ADMIN','MANAGER','EMPLOYEE','VIEWER') DEFAULT 'EMPLOYEE',
    expires_at TIMESTAMP NULL,
    status ENUM('PENDING','ACCEPTED','REVOKED','EXPIRED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    accepted_at TIMESTAMP NULL,

    CONSTRAINT fk_inv_frn FOREIGN KEY (id_franquicia)
        REFERENCES franquicias(id_franquicia)
        ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_inv_by FOREIGN KEY (invited_by)
        REFERENCES usuarios(id_usuario)
        ON DELETE SET NULL ON UPDATE CASCADE,

    INDEX idx_inv_email (invited_email),
    INDEX idx_inv_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: franchise_templates
-- ============================================================
CREATE TABLE IF NOT EXISTS franchise_templates (
    id_template INT AUTO_INCREMENT PRIMARY KEY,
    id_franquicia INT NULL,
    nombre VARCHAR(150) NOT NULL,
    tipo ENUM('BLOQUES_JSON','HTML_LIMITED') DEFAULT 'BLOQUES_JSON',
    contenido LONGBLOB NOT NULL,
    created_by INT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_tpl_frn FOREIGN KEY (id_franquicia)
        REFERENCES franquicias(id_franquicia)
        ON DELETE SET NULL ON UPDATE CASCADE,

    INDEX idx_tpl_frn (id_franquicia)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: faq
-- ============================================================
CREATE TABLE IF NOT EXISTS faq (
    id_faq BIGINT AUTO_INCREMENT PRIMARY KEY,
    question VARCHAR(500) NOT NULL,
    answer TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FULLTEXT KEY idx_faq_fulltext (question, answer)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: documento
-- ============================================================
CREATE TABLE IF NOT EXISTS documento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    slug VARCHAR(150) NOT NULL UNIQUE,
    tipo_contenido VARCHAR(100) NOT NULL,
    archivo LONGBLOB NOT NULL,
    fecha_subida TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: consultas
-- ============================================================
CREATE TABLE IF NOT EXISTS consultas (
    id_consulta INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    tipo_consulta ENUM('consulta','actualizacion','error','otro') NOT NULL,
    descripcion TEXT NOT NULL,
    respuesta TEXT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('PENDIENTE','EN_PROCESO','RESUELTO','CERRADO') DEFAULT 'PENDIENTE',

    CONSTRAINT fk_cons_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE,

    INDEX idx_cons_usuario (id_usuario),
    INDEX idx_cons_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: audit_logs
-- ============================================================
CREATE TABLE IF NOT EXISTS audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    actor_user_id INT NULL,
    action VARCHAR(200) NOT NULL,
    target_type VARCHAR(100) NULL,
    target_id VARCHAR(200) NULL,
    details JSON NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_audit_user FOREIGN KEY (actor_user_id)
        REFERENCES usuarios(id_usuario)
        ON DELETE SET NULL ON UPDATE CASCADE,

    INDEX idx_audit_actor (actor_user_id),
    INDEX idx_audit_action (action)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: system_settings
-- ============================================================
CREATE TABLE IF NOT EXISTS system_settings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    clave VARCHAR(150) NOT NULL UNIQUE,
    valor JSON NOT NULL,
    descripcion VARCHAR(300) NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- CREAR ÍNDICE: idx_frn_nombre (solo si no existe)
-- ============================================================
SET @idx_exists := (
    SELECT COUNT(1)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 'franquicias'
      AND index_name = 'idx_frn_nombre'
);

SET @create_idx := 'CREATE INDEX idx_frn_nombre ON franquicias (nombre_franquicia)';
PREPARE stmt FROM @create_idx;
IF @idx_exists = 0 THEN EXECUTE stmt; END IF;
DEALLOCATE PREPARE stmt;

-- ============================================================
-- FIN DEL ESQUEMA CENTRAL
-- ============================================================
