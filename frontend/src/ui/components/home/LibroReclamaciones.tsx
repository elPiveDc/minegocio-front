import React from "react";

const LibroReclamaciones: React.FC = () => {
  return (
    <div className="container py-5 mt-3">
      <div className="text-center mb-4">
        <h2 className="fw-bold">LIBRO DE RECLAMACIONES</h2>
        <p className="text-muted">Aquí puedes enviar tus consultas o quejas.</p>
      </div>

      <div className="row justify-content-center">
        <div className="col-12 col-lg-8">
          <div className="card shadow-lg border-0">
            <div className="card-body p-4">
              <form>
                <div className="mb-3">
                  <label htmlFor="nombre" className="form-label">
                    Nombre completo *
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="nombre"
                    placeholder="Ej. Juan Pérez"
                    required
                  />
                </div>

                <div className="mb-3">
                  <label htmlFor="correo" className="form-label">
                    Correo electrónico *
                  </label>
                  <input
                    type="email"
                    className="form-control"
                    id="correo"
                    placeholder="ejemplo@correo.com"
                    required
                  />
                </div>

                <div className="mb-3">
                  <label htmlFor="descripcion" className="form-label">
                    Descripción *
                  </label>
                  <textarea
                    className="form-control"
                    id="descripcion"
                    rows={4}
                    placeholder="Describa su consulta..."
                    required
                  ></textarea>
                </div>

                <button type="submit" className="btn btn-primary w-100">
                  Enviar Consulta
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LibroReclamaciones;
