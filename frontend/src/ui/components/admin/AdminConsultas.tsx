import React, { useState } from "react";
import { useAdminService } from "../../hooks/useAdmin";

import type { Consulta } from "../../../domain/models/Consulta";
import type { ConsultaRespuestaDto } from "../../../infrastructure/api/dto/FaqDocumentoConsulta.dto";

const AdminConsultas: React.FC = () => {
  // Hooks del servicio
  const {
    useObtenerTodasConsultas,
    useResponderConsulta,
    useCambiarEstadoConsulta,
  } = useAdminService();

  // Estados locales
  const [consultaSeleccionada, setConsultaSeleccionada] =
    useState<Consulta | null>(null);
  const [respuesta, setRespuesta] = useState("");

  // Obtener consultas (dominio, NO DTO)
  const { data: consultas, isLoading } = useObtenerTodasConsultas();

  // Mutations
  const responderConsultaMutation = useResponderConsulta();
  const cambiarEstadoMutation = useCambiarEstadoConsulta();

  // Handler para enviar respuesta
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!consultaSeleccionada) return;

    const payload: ConsultaRespuestaDto = { respuesta };

    responderConsultaMutation.mutate(
      { id: consultaSeleccionada.id, payload },
      {
        onSuccess: () => {
          alert("Respuesta enviada correctamente.");
          setRespuesta("");
          setConsultaSeleccionada(null);
        },
      }
    );
  };

  // Al hacer click en "Responder"
  const handleSeleccionarConsulta = (consulta: Consulta) => {
    setConsultaSeleccionada(consulta);
    setRespuesta(consulta.respuesta ?? "");
  };

  return (
    <>
      {/* ---------- FORMULARIO DE RESPUESTA ---------- */}
      <div className="card mb-4">
        <div className="card-body">
          <h5>Responder Consulta</h5>

          <form className="row g-2" onSubmit={handleSubmit}>
            <div className="col-12">
              <textarea
                className="form-control"
                rows={3}
                disabled
                value={
                  consultaSeleccionada?.descripcion ??
                  "Selecciona una consulta para ver su contenido."
                }
              />
            </div>

            <div className="col-12">
              <textarea
                className="form-control"
                rows={4}
                placeholder="Escribe la respuesta aquí..."
                value={respuesta}
                onChange={(e) => setRespuesta(e.target.value)}
                disabled={!consultaSeleccionada}
              />
            </div>

            <div className="col-12 text-end">
              <button
                className="btn btn-primary"
                disabled={!consultaSeleccionada || respuesta.trim() === ""}
              >
                Guardar Respuesta
              </button>
            </div>
          </form>
        </div>
      </div>

      {/* ---------- LISTADO DE CONSULTAS ---------- */}
      <div className="card">
        <div className="card-body">
          <h5 className="card-title">Listado de Consultas</h5>

          {isLoading ? (
            <p>Cargando consultas...</p>
          ) : (
            <table className="table table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Usuario</th>
                  <th>Tipo</th>
                  <th>Descripción</th>
                  <th>Respuesta</th>
                  <th>Estado</th>
                  <th>Acciones</th>
                </tr>
              </thead>

              <tbody>
                {consultas && consultas.length > 0 ? (
                  consultas.map((c) => (
                    <tr key={c.id}>
                      <td>{c.id}</td>
                      <td>{c.usuarioId}</td>
                      <td>{c.tipo}</td>
                      <td>{c.descripcion}</td>
                      <td>{c.respuesta ?? "Pendiente"}</td>
                      <td>{c.estado}</td>

                      <td>
                        <button
                          className="btn btn-sm btn-outline-secondary me-2"
                          onClick={() => handleSeleccionarConsulta(c)}
                        >
                          Responder
                        </button>

                        {/* Cambiar estado rápido */}
                        <button
                          className="btn btn-sm btn-outline-primary"
                          onClick={() =>
                            cambiarEstadoMutation.mutate({
                              id: c.id,
                              payload: { estado: "EN_PROCESO" },
                            })
                          }
                        >
                          En proceso
                        </button>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan={7} className="text-center text-muted">
                      No hay consultas.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          )}
        </div>
      </div>
    </>
  );
};

export default AdminConsultas;
