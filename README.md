
# 📘 MiNegocio.com – Plataforma Completa (Frontend + Backend + Minijuego)

Este repositorio contiene **tres proyectos independientes**, pero diseñados para funcionar juntos dentro del ecosistema **MiNegocio.com**, una plataforma moderna para la gestión de pequeños negocios y franquicias.

Los tres módulos incluidos son:

1. **Frontend – React + TypeScript (Aplicación principal del sistema)**
2. **Backend – Spring Boot (API REST segura con JWT + MySQL + Flyway)**
3. **Minijuego – React + TypeScript + JSON Server (Opcional / demostración técnica)**

Cada proyecto incluye su propio README con instrucciones completas de instalación y arquitectura.

---

## 🧱 Arquitectura General del Repositorio

```
/
├── backend/      → API REST en Spring Boot
├── frontend/     → Aplicación web principal
├── juego/        → Minijuego opcional (independiente)
└── sql.txt       → Script SQL inicial (para pruebas / desarrollo)

```

---

# 📌 1. Frontend – React + TypeScript (MiNegocio.com)

### ✔️ Propósito

Aplicación web moderna para gestionar:

* Usuarios y roles
* Franquicias
* Módulos del sistema
* Configuración de bases de datos por franquicia
* Documentos, consultas, auditoría, etc.

Implementado con arquitectura modular y siguiendo principios de Clean Architecture simplificada.

### ✔️ Tecnologías principales

* React 18
* TypeScript
* Vite
* JWT Authentication
* Bootstrap / CSS
* Hooks + Context API
* Modularización (domain / services / ui / infrastructure)

🔗 **README completo del frontend:**

> `/frontend/README.md`

---

# 📌 2. Backend – Spring Boot (API REST empresarial)

### ✔️ Propósito

Provee la API REST para la administración del sistema, incluyendo:

* Autenticación con JWT
* Gestión de usuarios
* Gestión de franquicias y sus BD independientes (multitenancy ligero)
* Sistemas de invitaciones
* Auditoría
* Módulos y configuraciones dinámicas
* Migraciones con Flyway

### ✔️ Tecnologías principales

* Java 17+
* Spring Boot 3
* Spring Security + JWT
* JPA / Hibernate
* MySQL 8
* Flyway
* Arquitectura en capas (Controller – Service – Repository – Model)

### ✔️ Configuración destacada

Incluye:

* CORS configurado para desarrollo (localhost:5174)
* Validación y excepciones centralizadas
* Multitenancy simplificado vía configuración dinámica
* Seguridad con filtros JWT personalizados

🔗 **README completo del backend:**

> `/backend/README.md`

---

# 🎮 3. Minijuego – React + TypeScript + JSON Server

*(Módulo opcional e independiente)*

Este proyecto NO depende del backend ni del frontend, pero puede integrarse como:

* Easter egg dentro del sistema
* Página de mantenimiento
* Actividad 404
* Demostración técnica de arquitectura limpia
* Muestra de consumo de API y ranking

### ✔️ Características

* Minijuego tipo shooter
* Arquitectura limpia (DDD simplificado)
* Uso de json-server como API de puntuaciones
* UI animada (Bootstrap Icons + animate.css)
* Ejemplo práctico de separación domain / infra / ui

### ✔️ Tecnologías

* React + TS
* Vite
* Hooks personalizados
* json-server
* CSS puro

🔗 **README completo del minijuego:**

> `/juego/README.md`

---

# 🔗 Relación entre los 3 proyectos

| Proyecto     | Independiente | Se integra con | Descripción                              |
| ------------ | ------------- | -------------- | ---------------------------------------- |
| **frontend** | ✔️ Sí         | backend        | Web principal del sistema                |
| **backend**  | ✔️ Sí         | frontend       | API REST empresarial                     |
| **juego**    | ✔️ Sí         | *(opcional)*   | Minijuego demostrativo, ranking opcional |

* El **frontend** consume al **backend** mediante JWT.
* El **juego** no depende de ninguno; trae su propio API fake (json-server).
* Puedes incrustar el juego en el frontend si deseas, pero no es obligatorio (btn que te redirige a la pagina de juego en el componente 404).

---

# 🛠️ Instalación Rápida (Vista General)

Para detalles completos revisa los README individuales.

---

## 🔵 Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Requisitos:

* MySQL 8+
* Java 17+
* Configurar `application.properties` si necesitas credenciales propias

---

## 🟠 Frontend

```bash
cd frontend
npm install
npm run dev
```

---

## 🟢 Minijuego

```bash
cd juego
npm install
npm run dev
json-server --watch db.json --port 3000
```

---

# 🧪 Base de Datos (MySQL)

El repositorio incluye un archivo:

```
sql.txt
```

Este contiene la estructura inicial del esquema central del sistema (`sistema_franquicias`).

---

# 📄 Licencia

Proyecto académico / experimental.
Libre para uso personal, educativo o de aprendizaje.

---

# 🎯 Contribuciones

Si deseas ampliar el proyecto, puedes contribuir en cualquiera de estos módulos:

* UI más modular
* CRUD adicionales en backend
* Mejoras de seguridad
* Nuevos niveles para el minijuego
* Integración del ranking real desde backend

---
