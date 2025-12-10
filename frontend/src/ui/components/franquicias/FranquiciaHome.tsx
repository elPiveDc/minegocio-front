import React from "react";

const FranquiciaHome: React.FC = () => {
  return (
    <div className="animate__animated animate__fadeIn">
      <div className="col d-flex justify-content-center">
        <h2 className="fw-bold mb-4 text-primary">Resumen General</h2>
      </div>

      <div className="row g-4">
        {/* Productos */}
        <div className="col-12 col-sm-6 col-lg-4">
          <div className="card border-0 shadow-lg rounded-4 p-4 pe-5 pt-5 position-relative overflow-hidden h-100">
            <div className="position-absolute top-0 end-0 opacity-10 pe-3 pt-2">
              <i className="bi bi-box-seam display-4 text-primary"></i>
            </div>

            <h5 className="fw-bold mb-1">Productos</h5>
            <p className="text-muted mb-2">120 productos registrados</p>

            <span className="badge bg-primary px-3 py-2 rounded-pill">
              Inventario
            </span>
          </div>
        </div>

        {/* Ventas */}
        <div className="col-12 col-sm-6 col-lg-4">
          <div className="card border-0 shadow-lg rounded-4 p-4 pe-5 pt-5 position-relative overflow-hidden h-100">
            <div className="position-absolute top-0 end-0 opacity-10 pe-3 pt-2">
              <i className="bi bi-graph-up-arrow display-4 text-success"></i>
            </div>

            <h5 className="fw-bold mb-1">Usuarios</h5>
            <p className="text-muted mb-2">Un usuario registrado</p>

            <span className="badge bg-success px-3 py-2 rounded-pill">
              Estadísticas
            </span>
          </div>
        </div>

        {/* Módulos Activos */}
        <div className="col-12 col-sm-6 col-lg-4">
          <div className="card border-0 shadow-lg rounded-4 p-4 pe-5 pt-5 position-relative overflow-hidden h-100">
            <div
              className="position-absolute top-0 end-0 opacity-10 pe-3 pt-2"
              style={{ fontSize: "3rem" }}
            >
              <i className="bi bi-grid-fill text-warning"></i>
            </div>

            <h5 className="fw-bold mb-3">Módulos activos</h5>

            <ul className="list-group list-group-flush mb-3">
              <li className="list-group-item px-0 border-0 text-muted">
                • Usuarios
              </li>
              <li className="list-group-item px-0 border-0 text-muted">
                • Inventario (Productos)
              </li>
            </ul>

            <span className="badge bg-warning text-dark px-3 py-2 rounded-pill">
              + Añadir
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FranquiciaHome;
