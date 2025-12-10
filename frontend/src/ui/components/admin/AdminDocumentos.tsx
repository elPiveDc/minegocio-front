import React, { useState } from "react";
import { useAdminService } from "../../hooks/useAdmin";
import type { DocumentoRequestDto } from "../../../infrastructure/api/dto/FaqDocumentoConsulta.dto";

const AdminDocumentos: React.FC = () => {
  const {
    useObtenerDocumentos,
    useCrearDocumento,
    useActualizarDocumento,
    useEliminarDocumento,
  } = useAdminService();

  // Query para cargar documentos
  const { data: documentos, isLoading, error } = useObtenerDocumentos();

  // Mutations
  const crearDocumento = useCrearDocumento();
  const actualizarDocumento = useActualizarDocumento();
  const eliminarDocumento = useEliminarDocumento();

  const [form, setForm] = useState<DocumentoRequestDto>({
    titulo: "",
    slug: "",
    tipoContenido: "",
    archivo: "",
  });

  const [editId, setEditId] = useState<number | null>(null);

  // Convertir archivo a base64
  const handleFileChange = async (file: File | null) => {
    if (!file) return;

    const reader = new FileReader();
    reader.onload = () => {
      const result = reader.result as string;
      const base64 = result.split(",")[1];

      setForm((prev) => ({
        ...prev,
        archivo: base64, // <--- SOLO base64 puro
        tipoContenido: file.type,
      }));
    };

    reader.readAsDataURL(file);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (editId === null) {
      crearDocumento.mutate(form, {
        onSuccess: () => {
          setForm({ titulo: "", slug: "", tipoContenido: "", archivo: "" });
        },
      });
    } else {
      actualizarDocumento.mutate(
        { id: editId, payload: form },
        {
          onSuccess: () => {
            setEditId(null);
            setForm({ titulo: "", slug: "", tipoContenido: "", archivo: "" });
          },
        }
      );
    }
  };

  const handleDelete = (id: number) => {
    eliminarDocumento.mutate(id);
  };

  return (
    <>
      {/* Formulario */}
      <div className="card mb-4">
        <div className="card-body">
          <h5>{editId ? "Editar Documento" : "Subir Documento"}</h5>

          <form className="row g-2" onSubmit={handleSubmit}>
            <div className="col-md-4">
              <input
                type="text"
                className="form-control"
                placeholder="Título"
                value={form.titulo}
                onChange={(e) =>
                  setForm((prev) => ({ ...prev, titulo: e.target.value }))
                }
              />
            </div>

            <div className="col-md-3">
              <input
                type="text"
                className="form-control"
                placeholder="Slug"
                value={form.slug}
                onChange={(e) =>
                  setForm((prev) => ({ ...prev, slug: e.target.value }))
                }
              />
            </div>

            <div className="col-md-4">
              <input
                type="file"
                className="form-control"
                onChange={(e) => handleFileChange(e.target.files?.[0] || null)}
              />
            </div>

            <div className="col-md-1 text-end">
              <button
                className="btn btn-primary"
                disabled={
                  crearDocumento.isPending || actualizarDocumento.isPending
                }
              >
                {editId ? "Editar" : "Subir"}
              </button>
            </div>

            {(crearDocumento.error || actualizarDocumento.error) && (
              <div className="text-danger mt-2">
                {(crearDocumento.error as Error)?.message ||
                  (actualizarDocumento.error as Error)?.message}
              </div>
            )}
          </form>
        </div>
      </div>

      {/* Listado */}
      <div className="card">
        <div className="card-body">
          <h5 className="card-title">Listado de Documentos</h5>

          {isLoading ? (
            <p>Cargando...</p>
          ) : error ? (
            <p className="text-danger">{(error as Error).message}</p>
          ) : (
            <table className="table table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Título</th>
                  <th>Slug</th>
                  <th>Tipo</th>
                  <th>Fecha subida</th>
                  <th>Acciones</th>
                </tr>
              </thead>

              <tbody>
                {!documentos || documentos.length === 0 ? (
                  <tr>
                    <td colSpan={6} className="text-center text-muted">
                      No hay documentos.
                    </td>
                  </tr>
                ) : (
                  documentos.map((doc) => (
                    <tr key={doc.id}>
                      <td>{doc.id}</td>
                      <td>{doc.titulo}</td>
                      <td>{doc.slug}</td>
                      <td>{doc.tipoContenido}</td>
                      <td>{new Date(doc.fechaSubida).toLocaleDateString()}</td>
                      <td>
                        <button
                          className="btn btn-sm btn-outline-secondary me-1"
                          onClick={() => {
                            setEditId(doc.id);
                            setForm({
                              titulo: doc.titulo,
                              slug: doc.slug,
                              tipoContenido: doc.tipoContenido,
                              archivo: "", // archivo base64 no viene en metadata
                            });
                          }}
                        >
                          Editar
                        </button>

                        <button
                          className="btn btn-sm btn-danger"
                          disabled={eliminarDocumento.isPending}
                          onClick={() => handleDelete(doc.id)}
                        >
                          Eliminar
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          )}
        </div>
      </div>
    </>
  );
};

export default AdminDocumentos;
