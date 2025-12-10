import { useState } from "react";
import { Link } from "react-router-dom";
import { useFranquicia } from "../../../hooks/useUsuarioFranquicia";

const RegistrarFranquicia: React.FC = () => {
  const { registrarFranquicia, isLoading, error, data } = useFranquicia();

  const franquiciasUsuario = [{ nombre: "Chocotejas", slug: "chocotejas" }];

  const [creando, setCreando] = useState(false);
  const [step, setStep] = useState(0);

  const [form, setForm] = useState({
    nombreFranquicia: "",
    slug: "",
    descripcion: "",
    templateId: 1,
  });

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const next = () => step < 3 && setStep(step + 1);
  const prev = () => step > 0 && setStep(step - 1);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const result = await registrarFranquicia(form);

    // Si se creó correctamente, mostramos la vista de "Mis Franquicias"
    if (result) {
      setCreando(true);
    }
  };

  if (creando) {
    return (
      <div className="row justify-content-center">
        <div className="col-12 col-md-8 col-lg-6">
          <div className="card shadow-sm border-0 p-4">
            <h3 className="text-center text-primary mb-4">
              <i className="bi bi-building me-2"></i>
              Mis Franquicias
            </h3>

            <ul className="list-group mb-4">
              {franquiciasUsuario.map((f) => (
                <li
                  key={f.slug}
                  className="list-group-item d-flex justify-content-between align-items-center"
                >
                  <span className="fw-semibold">{f.nombre}</span>
                  <Link
                    to={`/franquicia/${f.slug}`}
                    className="btn btn-primary btn-sm"
                  >
                    Entrar
                  </Link>
                </li>
              ))}
            </ul>

            <button
              className="btn btn-success w-100"
              onClick={() => {
                setForm({
                  nombreFranquicia: "",
                  slug: "",
                  descripcion: "",
                  templateId: 1,
                });
                setStep(0);
                setCreando(false);
              }}
            >
              <i className="bi bi-plus-circle me-2"></i>
              Crear nueva franquicia
            </button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="row justify-content-center">
      <div className="col-12 col-md-8 col-lg-6">
        <div className="card shadow-sm border-0 p-4">
          <h3 className="text-center text-primary mb-4">
            <i className="bi bi-gear-fill me-2"></i>
            Registro de Nueva Franquicia
          </h3>

          {/* ALERTAS */}
          {error && (
            <div className="alert alert-danger text-center">
              <i className="bi bi-exclamation-triangle-fill me-2"></i>
              {error}
            </div>
          )}

          {data && typeof data === "string" && (
            <div className="alert alert-success text-center">
              <i className="bi bi-check-circle-fill me-2"></i>
              {data}
            </div>
          )}

          {/* PROGRESO */}
          <div className="progress mb-4" style={{ height: "8px" }}>
            <div
              className="progress-bar bg-primary"
              style={{ width: `${(step + 1) * 25}%` }}
            ></div>
          </div>

          <form onSubmit={handleSubmit}>
            {/* PASO 1 */}
            {step === 0 && (
              <div className="mb-3">
                <label className="form-label fw-semibold">
                  Nombre de la Franquicia
                </label>
                <input
                  type="text"
                  className="form-control"
                  name="nombreFranquicia"
                  value={form.nombreFranquicia}
                  onChange={handleChange}
                  placeholder="Ej: Mi Tienda Express"
                  required
                />
              </div>
            )}

            {/* PASO 2 */}
            {step === 1 && (
              <div className="mb-3">
                <label className="form-label fw-semibold">Slug (URL)</label>
                <input
                  type="text"
                  className="form-control"
                  name="slug"
                  value={form.slug}
                  onChange={handleChange}
                  placeholder="ej: mi-tienda-express"
                  pattern="^[a-zA-Z0-9\\-]+$"
                  title="Solo letras, números y guiones"
                  required
                />
              </div>
            )}

            {/* PASO 3 */}
            {step === 2 && (
              <div className="mb-3">
                <label className="form-label fw-semibold">Descripción</label>
                <textarea
                  className="form-control"
                  name="descripcion"
                  rows={3}
                  value={form.descripcion}
                  onChange={handleChange}
                  placeholder="Describe brevemente la franquicia..."
                />
              </div>
            )}

            {/* PASO 4 */}
            {step === 3 && (
              <div className="text-center mb-3">
                <h5 className="text-success fw-semibold">
                  <i className="bi bi-check-circle-fill me-2"></i>
                  Todo listo
                </h5>
                <p className="text-muted">
                  Al hacer clic en <strong>Finalizar Registro</strong>, la
                  franquicia será creada.
                </p>
              </div>
            )}

            {/* BOTONES */}
            <div className="d-flex justify-content-between mt-4">
              {step > 0 ? (
                <button
                  type="button"
                  className="btn btn-outline-secondary"
                  onClick={prev}
                >
                  <i className="bi bi-arrow-left me-1"></i>Atrás
                </button>
              ) : (
                <button
                  type="button"
                  className="btn btn-outline-dark"
                  onClick={() => setCreando(true)}
                >
                  <i className="bi bi-arrow-left me-1"></i>Volver
                </button>
              )}

              {step < 3 ? (
                <button
                  type="button"
                  className="btn btn-primary"
                  onClick={next}
                >
                  Siguiente <i className="bi bi-arrow-right ms-1"></i>
                </button>
              ) : (
                <button
                  type="submit"
                  className="btn btn-success"
                  disabled={isLoading}
                >
                  {isLoading ? "Registrando..." : "Finalizar Registro"}
                </button>
              )}
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default RegistrarFranquicia;
