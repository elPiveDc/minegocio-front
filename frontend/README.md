
# **Frontend – Minegocio App**

Aplicación frontend desarrollada con **React + TypeScript + Vite**, que consume una API REST construida con **Spring Boot + JWT Authentication**.
El objetivo del proyecto es ofrecer una plataforma donde usuarios, franquicias y administradores puedan interactuar mediante un flujo seguro basado en tokens.

---

## ✨ **Tecnologías principales**

| Tecnología                        | Uso                                                       |
| --------------------------------- | --------------------------------------------------------- |
| **React 19 + TypeScript**         | Base del frontend, componentes y tipado                   |
| **Vite**                          | Empaquetador rápido y moderno                             |
| **Bootstrap 5 + Bootstrap Icons** | Estilos, layout y componentes UI                          |
| **Axios**                         | Cliente HTTP para consumo de la API                       |
| **React Router v7**               | Navegación entre páginas                                  |
| **React Hook Form**               | Manejo eficiente de formularios                           |
| **TanStack React Query**          | Manejo de cache, fetching y sincronización con el backend |
| **JWT Decode**                    | Decodificar tokens JWT en el cliente                      |
| **Animate.css**                   | Animaciones ligeras                                       |

---

# 🚀 **Arquitectura del Proyecto**

La arquitectura está basada en principios inspirados en **Clean Architecture**, separando responsabilidades en capas.

```
src/
  domain/
    models/        → Entidades y modelos tipados
  infrastructure/
    api/            → Axios + configuración de endpoints
    mappers/        → Transformación de datos API ↔ modelos
    services/       → Casos de uso que consumen la API
  ui/
    components/     → Componentes reutilizables
    context/        → Contextos globales (auth, usuario, etc)
    hooks/          → Hooks personalizados
    pages/          → Páginas completas
    router/         → Enrutadores principales
  utils/            → Funciones auxiliares
```

### **Objetivo de la arquitectura**

* Separar reglas de negocio del framework (React).
* Facilitar testing.
* Reducir acoplamiento entre UI y backend.
* Permitir reemplazar backend/API sin romper la UI.

---

# 🔐 **Autenticación y flujo con JWT**

El backend en **Spring Boot** genera un **JWT** cuando el usuario inicia sesión.

### **Flujo del token:**

1. El usuario inicia sesión desde `/minegocio/login`.
2. El frontend envía credenciales al backend via `axios.post()`.
3. Spring Boot valida y responde con:

   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
   }
   ```
4. El token se guarda en `localStorage`.
5. Se decodifica con `jwt-decode` para obtener:

   * rol
   * id usuario
   * fecha de expiración
6. Todas las peticiones posteriores incluyen:

   ```ts
   Authorization: Bearer <token>
   ```
7. Si el token expira, el usuario es redirigido al login.

---

# 🧭 **Sistema de Rutas**

La app tiene varios routers separados por dominios:

## **AppRouter.tsx**

Router raíz:

* `/minegocio/*` → público + dashboard usuario
* `/franquicia/*` → zona de franquicias específicas
* `/admin/` → protegido, solo usuarios con rol `ADMIN`
* `/pruebas/*`
* `/404`
* `*` redirige a `/minegocio`

## **MinegocioRouter.tsx**

Encargado de toda la navegación del **portal principal**.

Incluye:

* Home (`/minegocio/`)
* Login y Registro
* Preguntas frecuentes
* Documentos (Términos, Privacidad)
* Dashboard del usuario (requiere estar logueado)

Estructura de rutas anidadas:

```
/minegocio/
    login
    register
    libro-reclamaciones
    preguntas
    documentos/
        terminos
        privacidad
    dashboard/
        usuario
        invitaciones
        gestiona-franquicia
```

## **ProtectedRoute.tsx**

Componente que:

* Verifica si existe token JWT
* Verifica si el rol coincide con el requerido
* Si falla → redirige a `/404` o `/login`

Ejemplo:

```tsx
<ProtectedRoute requiredRole="ADMIN">
  <AdminHome />
</ProtectedRoute>
```

---

# 📦 **Gestión de estado y fetching (React Query)**

El proyecto utiliza **React Query** para:

* Caching de peticiones
* Reintentos automáticos
* Control de estados: `isLoading`, `isError`, `data`

Configuración en `App.tsx`:

```tsx
<QueryClientProvider client={queryClient}>
  <AppRouter />
</QueryClientProvider>
```

---

# 🧩 **Consumo del Backend (Spring Boot)**

Las llamadas HTTP están organizadas en:

### **`infrastructure/api`**

Define:

* instancia de axios (`axiosInstance`)
* interceptores para agregar JWT
* configuración baseURL

### **`infrastructure/services`**

Servicios especializados.
Ejemplo: autenticación, usuarios, franquicias.

```ts
export const login = async (email, password) =>
  axiosInstance.post("/auth/login", { email, password });
```

### **`infrastructure/mappers`**

Transforman respuestas de la API a modelos del dominio.

---

# 🗂️ **Scripts disponibles**

En `package.json`:

| Script            | Descripción                       |
| ----------------- | --------------------------------- |
| `npm run dev`     | Ejecuta el servidor de desarrollo |
| `npm run build`   | Genera el build de producción     |
| `npm run preview` | Previsualiza el build             |
| `npm run lint`    | Ejecuta ESLint                    |

---

# 🛠️ **Instalación y configuración**

### **1. Clonar el repositorio**

```sh
git clone https://github.com/elPiveDc/minegocio-front.git
cd frontend
```

### **2. Instalar dependencias**

```sh
npm install
```

### **3. Ejecutar el proyecto**

```sh
npm run dev
```

---

# 📱 **Estructura de UI**

### Componentes principales:

* **Login/Register**
* **Dashboard del usuario**
* **Gestión de franquicias**
* **Vistas públicas (Home, Documentos, Preguntas)**

### Estilos:

* Bootstrap 5
* Animaciones con Animate.css
* Layout responsivo

---

# 👤 **Roles y permisos**

El frontend consume roles del JWT:

| Rol          | Acceso                        |
| ------------ | ----------------------------- |
| `USER`       | Dashboard personal            |
| `FRANQUICIA` | Gestión de franquicias        |
| `ADMIN`      | Panel administrativo completo |

---

# 🧪 **Pruebas**

La carpeta `/pruebas` contiene vistas usadas para testear componentes y servicios durante el desarrollo.


