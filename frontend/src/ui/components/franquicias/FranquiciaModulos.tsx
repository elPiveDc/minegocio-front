import React from "react";

const FranquiciaModulos: React.FC = () => {
  return (
    <div className="d-flex flex-column min-vh-100 bg-light">
      {/* MAIN */}
      <main className="flex-grow-1 d-flex justify-content-center align-items-start">
        <div className="container py-5">
          <div className="col d-flex justify-content-center">
            <h2 className="mb-4 fw-bold text-primary">Módulos</h2>
          </div>

          {/* Contenedor de Módulos */}
          <div className="row g-4">
            {/* Módulo: Facturas */}
            <div className="col-12 col-sm-6 col-md-4">
              <div
                className="card text-center border-0 shadow-sm rounded-4 p-3 module-card"
                role="button"
              >
                <div className="card-body">
                  <i className="bi bi-receipt-cutoff text-primary fs-1"></i>
                  <h5 className="mt-3 fw-semibold">Módulo de Facturas</h5>
                </div>
              </div>
            </div>

            {/* Módulo: Inventario */}
            <div className="col-12 col-sm-6 col-md-4">
              <div
                className="card text-center border-0 shadow-sm rounded-4 p-3 module-card"
                role="button"
              >
                <div className="card-body">
                  <i className="bi bi-box-seam text-success fs-1"></i>
                  <h5 className="mt-3 fw-semibold">Módulo de Inventario</h5>
                </div>
              </div>
            </div>

            {/* Agregar Módulo */}
            <div className="col-12 col-sm-6 col-md-4">
              <div
                className="card text-center border-0 bg-white shadow-sm rounded-4 p-3 module-card"
                role="button"
                data-bs-toggle="modal"
                data-bs-target="#modalCrearModulo"
              >
                <div className="card-body">
                  <i className="bi bi-plus-lg text-secondary fs-1"></i>
                  <h5 className="mt-3 fw-semibold">Agregar Módulo</h5>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Modal Crear Módulo */}
        <div
          className="modal fade"
          id="modalCrearModulo"
          tabIndex={-1}
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content rounded-4">
              <div className="modal-header">
                <h5 className="modal-title fw-semibold">Crear Módulo</h5>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                ></button>
              </div>

              <div className="modal-body">
                <p className="mb-3">
                  Seleccione el tipo de módulo que desea agregar:
                </p>

                <div className="d-grid gap-3">
                  <button className="btn btn-outline-primary py-2">
                    <i className="bi bi-receipt-cutoff me-2"></i>
                    Módulo de Facturas
                  </button>

                  <button className="btn btn-outline-success py-2">
                    <i className="bi bi-box-seam me-2"></i>
                    Módulo de Inventario
                  </button>

                  <button className="btn btn-outline-warning py-2">
                    <i className="bi bi-bar-chart-line me-2"></i>
                    Módulo de Reportes
                  </button>
                </div>
              </div>

              <div className="modal-footer">
                <button className="btn btn-secondary" data-bs-dismiss="modal">
                  Cerrar
                </button>
              </div>
            </div>
          </div>
        </div>
      </main>

      {/* Hover CSS Inline */}
      <style>
        {`
          .module-card {
            transition: transform .15s ease, box-shadow .15s ease;
          }

          .module-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 0.75rem 1.25rem rgba(0,0,0,0.12);
          }
        `}
      </style>
    </div>
  );
};

export default FranquiciaModulos;
