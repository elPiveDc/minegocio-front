import React from "react";
import { useAuthContext } from "../../../hooks/useAuthContext";

const UsuarioPage: React.FC = () => {
  const { isAuthenticated, user, isLoading, error } = useAuthContext();

  if (isLoading) return <p>Cargando información del usuario...</p>;
  if (error) return <p className="text-danger">Error: {error}</p>;
  if (!isAuthenticated || !user) return <p>No hay un usuario autenticado.</p>;

  // Valores por defecto si faltan campos
  const {
    nombre = "Usuario genérico",
    correo = "correo@ejemplo.com",
    rol = "INVITADO",
  } = user;

  // Otros campos que no están en MeResponse los simulamos con defaults
  const estado = "ACTIVO";
  const emailVerificado = false;
  const fechaRegistro = new Date().toISOString();
  const lastLogin = null;
  const avatarUrl = null;
  const zonaHoraria = "No especificada";

  return (
    <div className="card p-4 shadow-sm">
      {/* Encabezado con avatar */}
      <div className="d-flex align-items-center mb-4">
        <img
          src={avatarUrl ?? "/img/perfilgenerico.jpeg"}
          alt="Avatar"
          className="rounded-circle me-3"
          width={75}
          height={75}
        />
        <div>
          <h4 className="mb-1">{nombre}</h4>
          <span className="text-muted">{correo}</span>
        </div>
      </div>

      <hr />

      {/* Estado del usuario */}
      <div className="row mb-3">
        <div className="col-md-6">
          <p className="mb-1">
            <strong>Estado:</strong>
          </p>
          <span className="badge px-3 py-2 bg-success">{estado}</span>
        </div>

        <div className="col-md-6">
          <p className="mb-1">
            <strong>Email verificado:</strong>
          </p>
          <span className={emailVerificado ? "text-success" : "text-danger"}>
            {emailVerificado ? "Sí, verificado" : "No verificado"}
          </span>
        </div>
      </div>

      {/* Fechas */}
      <div className="row mb-3">
        <div className="col-md-6">
          <p className="mb-1">
            <strong>Fecha de registro:</strong>
          </p>
          <span>{new Date(fechaRegistro).toLocaleString()}</span>
        </div>
        <div className="col-md-6">
          <p className="mb-1">
            <strong>Último inicio de sesión:</strong>
          </p>
          <span>
            {lastLogin
              ? new Date(lastLogin).toLocaleString()
              : "Nunca ha iniciado sesión"}
          </span>
        </div>
      </div>

      {/* Zona horaria */}
      <div>
        <p className="mb-1">
          <strong>Zona horaria:</strong>
        </p>
        <span>{zonaHoraria}</span>
      </div>

      {/* Rol */}
      <div className="mt-3">
        <p className="mb-1">
          <strong>Rol:</strong>
        </p>
        <span>{rol}</span>
      </div>
    </div>
  );
};

export default UsuarioPage;
