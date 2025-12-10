import React from "react";
import { Navigate } from "react-router-dom";
import { useAuthContext } from "../../hooks/useAuthContext";

export interface ProtectedRouteProps {
  children: React.ReactNode;
  requiredRole?: string;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({
  children,
  requiredRole,
}) => {
  const { user, isAuthenticated } = useAuthContext();

  if (!isAuthenticated) {
    return <Navigate to="/minegocio" replace />;
  }

  if (requiredRole && user?.rol == "USUARIO") {
    return (
      <div className="d-flex flex-column align-items-center mt-5">
        <h3 className="text-danger">Acceso denegado</h3>
        <p>No tienes permisos para acceder a esta sección.</p>
      </div>
    );
  }

  return <>{children}</>;
};

export default ProtectedRoute;
