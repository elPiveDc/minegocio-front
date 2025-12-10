import React, { useState } from "react";
import { Link, Outlet } from "react-router-dom";

const Franquicia: React.FC = () => {
  const [open, setOpen] = useState(false);

  return (
    <div className="min-vh-100 d-flex position-relative">
      <button
        className="btn btn-primary position-fixed m-3"
        style={{ zIndex: 1100 }}
        onClick={() => setOpen(!open)}
      >
        {open ? "Cerrar" : "☰ Menú"}
      </button>

      <aside
        className={`bg-dark text-white p-3 shadow-lg position-fixed top-0 h-100`}
        style={{
          width: "250px",
          left: open ? "0" : "-250px",
          transition: "left 0.3s ease",
        }}
      >
        <h4 className="text-center mt-5">Chocotejas</h4>

        <ul className="nav flex-column">
          <li className="nav-item">
            <Link
              className="nav-link text-white"
              to=""
              onClick={() => setOpen(false)}
            >
              🏠 Resumen General
            </Link>
          </li>

          <li className="nav-item">
            <Link
              className="nav-link text-white"
              to="productos"
              onClick={() => setOpen(false)}
            >
              📦 Productos
            </Link>
          </li>

          <li className="nav-item">
            <Link
              className="nav-link text-white"
              to="ventas"
              onClick={() => setOpen(false)}
            >
              👤 Usuarios
            </Link>
          </li>

          <li className="nav-item">
            <Link
              className="nav-link text-white"
              to="modulos"
              onClick={() => setOpen(false)}
            >
              🧩 Módulos
            </Link>
          </li>
          <Link to="/minegocio/dashboard" className="btn btn-success mt-2">
            Ver perfil
          </Link>
        </ul>
      </aside>

      <main
        className="flex-grow-1 p-4 bg-light w-100"
        style={{
          marginLeft: open ? "250px" : "0",
          transition: "margin-left 0.3s ease",
        }}
      >
        <Outlet />
      </main>
    </div>
  );
};

export default Franquicia;
