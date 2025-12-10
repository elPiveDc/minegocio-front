import { Outlet, NavLink, Link } from "react-router-dom";

const DashboardLayout: React.FC = () => {
  return (
    <div className="container py-4 min-vh-100">
      <div className="mt-3">
        <Link
          to="/minegocio"
          className="text-primary fw-semibold d-inline-flex align-items-center gap-1 text-decoration-none"
        >
          <i className="bi bi-chevron-left"></i>
          Volver al inicio
        </Link>
      </div>

      <div className="mt-2">
        <nav
          className="mb-4"
          role="navigation"
          aria-label="Dashboard navigation"
        >
          <ul className="nav nav-tabs">
            <li className="nav-item">
              <NavLink
                to="usuario"
                className={({ isActive }) =>
                  "nav-link" + (isActive ? " active fw-semibold" : "")
                }
              >
                Gestión de Usuario
              </NavLink>
            </li>

            <li className="nav-item">
              <NavLink
                to="invitaciones"
                className={({ isActive }) =>
                  "nav-link" + (isActive ? " active fw-semibold" : "")
                }
              >
                Invitaciones
              </NavLink>
            </li>

            <li className="nav-item">
              <NavLink
                to="gestiona-franquicia"
                className={({ isActive }) =>
                  "nav-link" + (isActive ? " active fw-semibold" : "")
                }
              >
                Gestion de Franquicia
              </NavLink>
            </li>
          </ul>
        </nav>
      </div>

      <Outlet />
    </div>
  );
};

export default DashboardLayout;
