import React, { useState } from "react";
import { useAdminService } from "../../hooks/useAdmin";

const AdminFAQ: React.FC = () => {
  const { useObtenerFaqs, useCrearFaq, useActualizarFaq, useEliminarFaq } =
    useAdminService();

  // Queries
  const { data: faqList, isLoading, error } = useObtenerFaqs();

  // Mutations
  const crearFaq = useCrearFaq();
  const actualizarFaq = useActualizarFaq();
  const eliminarFaq = useEliminarFaq();

  const [form, setForm] = useState({ question: "", answer: "" });
  const [editId, setEditId] = useState<number | null>(null);

  const handleSave = (e: React.FormEvent) => {
    e.preventDefault();

    if (editId === null) {
      // Crear FAQ
      crearFaq.mutate(form, {
        onSuccess: () => {
          setForm({ question: "", answer: "" });
        },
      });
    } else {
      // Editar FAQ
      actualizarFaq.mutate(
        { id: editId, payload: form },
        {
          onSuccess: () => {
            setEditId(null);
            setForm({ question: "", answer: "" });
          },
        }
      );
    }
  };

  const handleDelete = (id: number) => {
    eliminarFaq.mutate(id);
  };

  return (
    <>
      <div className="card mb-4">
        <div className="card-body">
          <h5>
            {editId ? "Editar Pregunta Frecuente" : "Nueva Pregunta Frecuente"}
          </h5>

          <form className="row g-2" onSubmit={handleSave}>
            <div className="col-12">
              <input
                type="text"
                className="form-control"
                placeholder="Pregunta"
                value={form.question}
                onChange={(e) =>
                  setForm((prev) => ({ ...prev, question: e.target.value }))
                }
              />
            </div>

            <div className="col-12">
              <textarea
                className="form-control"
                placeholder="Respuesta"
                rows={4}
                value={form.answer}
                onChange={(e) =>
                  setForm((prev) => ({ ...prev, answer: e.target.value }))
                }
              ></textarea>
            </div>

            <div className="col-12 text-end">
              <button
                className="btn btn-primary"
                disabled={crearFaq.isPending || actualizarFaq.isPending}
              >
                {editId ? "Actualizar" : "Guardar"}
              </button>
            </div>

            {(crearFaq.error || actualizarFaq.error) && (
              <div className="text-danger mt-2">
                {(crearFaq.error as Error)?.message ||
                  (actualizarFaq.error as Error)?.message}
              </div>
            )}
          </form>
        </div>
      </div>

      {/* Listado */}
      <div className="card">
        <div className="card-body">
          <h5 className="card-title">Listado de Preguntas Frecuentes</h5>

          {isLoading ? (
            <p>Cargando...</p>
          ) : error ? (
            <p className="text-danger">{(error as Error).message}</p>
          ) : (
            <table className="table table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Pregunta</th>
                  <th>Respuesta</th>
                  <th>Acciones</th>
                </tr>
              </thead>

              <tbody>
                {!faqList || faqList.length === 0 ? (
                  <tr>
                    <td colSpan={4} className="text-center text-muted">
                      No hay preguntas registradas.
                    </td>
                  </tr>
                ) : (
                  faqList.map((faq) => (
                    <tr key={faq.id}>
                      <td>{faq.id}</td>
                      <td>{faq.question}</td>
                      <td>{faq.answer}</td>
                      <td>
                        <button
                          className="btn btn-sm btn-outline-secondary me-1"
                          onClick={() => {
                            setEditId(faq.id);
                            setForm({
                              question: faq.question,
                              answer: faq.answer,
                            });
                          }}
                        >
                          Editar
                        </button>

                        <button
                          className="btn btn-sm btn-danger"
                          disabled={eliminarFaq.isPending}
                          onClick={() => handleDelete(faq.id)}
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

export default AdminFAQ;
