import React, { useEffect, useState } from "react";
import { useUsuarioInvitacion } from "../../../hooks/useUsuarioInvitacion";
import type { InvitationResponseDTO } from "../../../../infrastructure/api/types/invitaciones";

const InvitacionesPage: React.FC = () => {
  const { list, error, loading, aceptarInvitacion, listarPorFranquicia } =
    useUsuarioInvitacion();

  const [showModal, setShowModal] = useState(false);
  const [accepted, setAccepted] = useState<InvitationResponseDTO | null>(null);

  useEffect(() => {
    listarPorFranquicia(1);
  }, [listarPorFranquicia]);

  const getStatusBadge = (status: string) => {
    switch (status) {
      case "PENDING":
        return "badge bg-warning text-dark";
      case "ACCEPTED":
        return "badge bg-success";
      case "REVOKED":
        return "badge bg-secondary";
      case "EXPIRED":
        return "badge bg-danger";
      default:
        return "badge bg-light text-dark";
    }
  };

  const handleAceptar = async (token: string) => {
    try {
      const resp = await aceptarInvitacion(token);
      setAccepted(resp);
      setShowModal(true);
      // Opcional: refrescar listado
      listarPorFranquicia(resp.idFranquicia);
    } catch {
      // El error ya se maneja en el hook (setError)
    }
  };

  return (
    <div className="card p-4 shadow-sm">
      <h3 className="mb-3">Invitaciones a Franquicias</h3>

      {error && <div className="alert alert-danger">{error}</div>}
      {loading && <div className="alert alert-info">Cargando...</div>}

      <div className="table-responsive">
        <table className="table table-bordered align-middle">
          <thead className="table-light">
            <tr>
              <th>Email invitado</th>
              <th>Rol ofrecido</th>
              <th>Estado</th>
              <th>Fecha creación</th>
              <th>Expira</th>
              <th>Token</th>
              <th>Acciones</th>
            </tr>
          </thead>

          <tbody>
            {list?.map((inv) => (
              <tr key={inv.id}>
                <td>{inv.invitedEmail}</td>
                <td>{inv.roleOffered}</td>
                <td>
                  <span className={getStatusBadge(inv.status)}>
                    {inv.status}
                  </span>
                </td>
                <td>
                  {inv.createdAt
                    ? new Date(inv.createdAt).toLocaleString()
                    : "Sin fecha"}
                </td>
                <td>
                  {inv.expiresAt
                    ? new Date(inv.expiresAt).toLocaleString()
                    : "Sin fecha"}
                </td>
                <td>{inv.token.slice(0, 10)}...</td>
                <td>
                  {inv.status === "PENDING" && (
                    <button
                      className="btn btn-sm btn-primary"
                      onClick={() => handleAceptar(inv.token)}
                      disabled={loading}
                    >
                      {loading ? "Procesando..." : "Aceptar"}
                    </button>
                  )}
                </td>
              </tr>
            ))}

            {list.length === 0 && !loading && (
              <tr>
                <td colSpan={7} className="text-center text-muted">
                  No hay invitaciones para mostrar.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {showModal && (
        <div
          className="modal fade show d-block"
          tabIndex={-1}
          role="dialog"
          aria-modal="true"
        >
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Invitación aceptada</h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => setShowModal(false)}
                  aria-label="Cerrar"
                />
              </div>
              <div className="modal-body">
                <p>
                  La invitación para <strong>{accepted?.invitedEmail}</strong>{" "}
                  fue aceptada correctamente. Pronto se le asignarán los
                  permisos a la franquicia.
                </p>
              </div>
              <div className="modal-footer">
                <button
                  className="btn btn-secondary"
                  onClick={() => setShowModal(false)}
                >
                  Cerrar
                </button>
              </div>
            </div>
          </div>
          <div className="modal-backdrop fade show" />
        </div>
      )}
    </div>
  );
};

export default InvitacionesPage;
