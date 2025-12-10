import React, { useEffect, useState } from "react";
import {
  franquiciaService,
  type Usuario,
} from "../../../infrastructure/services/usuariofranquiciaServices";

const FranquiciaVentas: React.FC<{ idFranquicia?: number }> = ({
  idFranquicia = 1,
}) => {
  const [usuarios, setUsuarios] = useState<Usuario[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    franquiciaService
      .listarUsuariosPorFranquicia(idFranquicia)
      .then((data: Usuario[]) => {
        setUsuarios(data);
        setLoading(false);
      })
      .catch((err: unknown) => {
        console.error("Error cargando usuarios", err);
        setLoading(false);
      });
  }, [idFranquicia]);

  return (
    <div className="animate__animated animate__fadeIn">
      <div className="col d-flex justify-content-center">
        <h2 className="fw-bold mb-3 text-primary">Usuarios de la Franquicia</h2>
      </div>
      <p className="text-muted">Resumen general de usuarios registrados.</p>

      {/* Tabla */}
      <div className="card shadow-sm p-4 rounded-4">
        <h5 className="fw-semibold mb-3">Usuarios Registrados</h5>

        {loading ? (
          <p className="text-center text-muted">Cargando usuarios...</p>
        ) : (
          <div className="table-responsive">
            <table className="table table-striped table-hover align-middle">
              <thead className="table-info">
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Rol</th>
                  <th>Email</th>
                </tr>
              </thead>
              <tbody>
                {usuarios.length > 0 ? (
                  usuarios.map((u) => (
                    <tr key={u.id}>
                      <td>{u.id}</td>
                      <td>Mateo</td>
                      <td>{u.rol}</td>
                      <td>M@gmail.com</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan={4} className="text-center text-muted">
                      No hay usuarios registrados
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default FranquiciaVentas;
