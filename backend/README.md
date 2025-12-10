
# **Minegocio Backend – Spring Boot + JWT + MySQL + Multitenancy**

Backend del sistema **Minegocio**, encargado de la autenticación, gestión de usuarios, franquicias, módulos y operaciones administrativas.
Construido con **Spring Boot 3**, **JPA/Hibernate**, **JWT**, **Flyway**, **CORS** configurado para el frontend en Vite, y soporte para **multitenancy a nivel de base de datos**.

---

## 🚀 **Tecnologías principales**

| Tecnología                | Uso                                |
| ------------------------- | ---------------------------------- |
| **Spring Boot 3.5**       | API REST, seguridad, configuración |
| **Spring Security + JWT** | Autenticación y autorización       |
| **Spring Data JPA**       | Acceso a datos                     |
| **MySQL**                 | Base de datos principal            |
| **Flyway**                | Migraciones versionadas            |
| **Lombok**                | Reducción de boilerplate           |
| **Maven**                 | Gestión de dependencias            |
| **DevTools**              | Hot reload en desarrollo           |

---

# 📁 **Estructura del proyecto**

```
src/main/java/com/minegocio/backend
│
├── controller/
│     AdminController.java
│     AuthController.java
│     PublicosController.java
│     UsuarioConsultaController.java
│     UsuarioFranquiciaController.java
│     UsuarioInvitationController.java
│     UsuarioModuloController.java
│
├── dto/
│     (Clases para request/response sin exponer entidades)
│
├── model/
│     (Entidades JPA: Usuario, Franquicia, Modulo, etc.)
│
├── multitenant/
│     (Lógica para manejar múltiples tenants con MySQL)
│
├── repository/
│     (Repositorios JPA)
│
├── security/
│     SecurityConfig.java
│     jwt/
│         JwtAuthorizationFilter.java
│         JwtAuthenticationEntryPoint.java
│         JwtUtils.java
│
├── service/
│     (Reglas de negocio)
│
└── util/
      (Clases auxiliares)
```

---

# 🔐 **Seguridad y Autenticación (JWT)**

El backend usa **JWT** como mecanismo de autenticación.

### Flujo de autenticación:

1. El usuario envía email + password a `/api/auth/publicos/login`.
2. Spring Security valida credenciales con `AuthenticationManager`.
3. Se genera un JWT firmado con la clave configurada:

   ```
   jwt.secret=...
   ```
4. El token se devuelve al frontend.
5. En cada petición protegida debe enviarse:

   ```
   Authorization: Bearer <token>
   ```
6. El filtro `JwtAuthorizationFilter` valida el token y setea el usuario en el contexto.

### Roles soportados:

| Rol          | Acceso                                              |
| ------------ | --------------------------------------------------- |
| `USUARIO`    | Endpoints de usuario                                |
| `ADMIN`      | Administración completa                             |
| `FRANQUICIA` | Gestión de franquicias (si aplica en tus servicios) |

---

# 🔐 **SecurityConfig**

Tu configuración de seguridad funciona así:

* Desactiva CSRF (API REST)
* Habilita CORS para el frontend:

  ```
  http://localhost:5174
  ```
* Define rutas públicas:

  ```
  /api/publicos/**
  /api/auth/publicos/**
  ```
* Rutas protegidas:

  ```
  /api/auth/usuario/**
  /api/usuarios/**
  ```
* Rutas exclusivas de administrador:

  ```
  /api/admin/**
  ```
* Agrega el filtro JWT antes del filtro de usuario y contraseña.

Código relevante:

```java
.requestMatchers("/api/publicos/**").permitAll()
.requestMatchers("/api/auth/publicos/**").permitAll()

.requestMatchers("/api/auth/usuario/**").hasAnyRole("USUARIO", "ADMIN")
.requestMatchers("/api/usuarios/**").hasAnyRole("USUARIO", "ADMIN")

.requestMatchers("/api/admin/**").hasRole("ADMIN")
```

---

# 🗄️ **Base de Datos y JPA**

### Configuración de conexión

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_franquicias
spring.datasource.username=Prueba
spring.datasource.password=Prueba123
```

### Configuración JPA:

* `ddl-auto=none` (nada se crea automáticamente)
* SQL visible en consola
* Dialecto de MySQL8

---

# 🏗️ **Migraciones con Flyway**

Las migraciones se ubican en:

```
src/main/resources/db/migration
```

Y están habilitadas con:

```properties
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.validate-on-migrate=true
```

Cada migración debe tener formato:

```
V1__init.sql
V2__add_usuario_table.sql
```

---

# 🧩 **Multitenancy**

El backend incorpora soporte para multitenancy usando MySQL.

Propiedades:

```properties
multitenant.mysql.host=localhost
multitenant.mysql.port=3306
multitenant.mysql.user=Prueba
multitenant.mysql.password=Prueba123
```

El paquete:

```
multitenant/
```

Incluye:

* proveedores de conexión
* resolución de tenant según cabecera o contexto
* creación dinámica de datasources cuando sea necesario

---

# 📑 **Controladores principales**

### **1. PublicosController**

Rutas abiertas:

* información pública
* endpoints de prueba
* registro inicial

### **2. AuthController**

Autenticación:

* login
* refresh token (si lo implementas)
* registro

### **3. UsuarioConsultaController**

Información del usuario logueado:

* `/me`
* datos básicos
* invitaciones

### **4. UsuarioFranquiciaController**

Gestión de franquicias del usuario.

### **5. UsuarioInvitationController**

Sistema de invitaciones.

### **6. UsuarioModuloController**

Acceso y gestión de módulos.

### **7. AdminController**

Rutas exclusivas del rol ADMIN:

* gestión completa de usuarios
* activaciones
* vistas globales

---

# ⚙️ **application.properties**

Incluye:

* configuración del servidor
* BD
* multitenancy
* JWT
* flyway
* multipart

Ejemplo:

```properties
spring.application.name=minegocio-backend
server.port=8080

jwt.secret=***************
jwt.expiration=86400000
```

---

# 🧪 **Testing**

Incluye dependencias:

* `spring-boot-starter-test`
* `spring-security-test`

Para pruebas de controladores, seguridad y repositorios.

---

# 🛠️ **Ejecutar el proyecto**

### 1. Configurar MySQL

Crear la base de datos:

```sql
CREATE DATABASE sistema_franquicias;
```

### 2. Ajustar credenciales en `application.properties`

### 3. Ejecutar migraciones Flyway (automático al levantar)

### 4. Iniciar el backend:

```sh
mvn spring-boot:run
```

Servidor disponible en:

```
http://localhost:8080
```

---

# 🌐 **CORS**

Solo permite llamadas desde:

```
http://localhost:5174
```

Para evitar accesos no autorizados desde otros orígenes.

---

# 📌 **Integración con el frontend**

El frontend envía tokens JWT en cada petición:

```http
GET /api/usuarios/me
Authorization: Bearer <token>
```

El backend responde 401 si:

* el token está vencido
* el token es inválido
* falta autorización para el rol solicitado

---
