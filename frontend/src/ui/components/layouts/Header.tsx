import { Link } from "react-router-dom";
import { useAuthContext } from "../../hooks/useAuthContext";

const Header: React.FC = () => {
  const { isAuthenticated, user, logout } = useAuthContext();

  return (
    <header className="shadow-sm">
      <nav className="navbar navbar-expand-lg navbar-light bg-white py-3">
        <div className="container">
          <Link
            to="/"
            className="navbar-brand d-flex align-items-center text-decoration-none"
            style={{ transition: "transform 0.3s ease" }}
            onMouseOver={(e) =>
              (e.currentTarget.style.transform = "scale(1.05)")
            }
            onMouseOut={(e) => (e.currentTarget.style.transform = "scale(1)")}
          >
            <img
              src="../../../public/img/mi_negocio.png"
              alt="Logo MiNegocio"
              width={50}
              className="me-2"
            />
            <span className="fw-bold fs-4 text-primary mb-0">MiNegocio</span>
          </Link>

          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>

          {/* Menú - Lógica Condicional AQUÍ */}
          <div className="collapse navbar-collapse mt-3 mt-lg-0" id="navbarNav">
            <ul className="navbar-nav ms-auto align-items-lg-center gap-2 gap-lg-3">
              {/* Opción 1: Si NO está autenticado, muestra Registro e Inicio de Sesión */}
              {!isAuthenticated && (
                <>
                  <li className="nav-item">
                    <Link
                      className="nav-link fw-semibold px-3"
                      to="/minegocio/register"
                    >
                      Registro
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link
                      className="btn btn-primary ms-lg-3"
                      to="/minegocio/login"
                    >
                      Iniciar Sesión
                    </Link>
                  </li>
                </>
              )}

              {/* Opción 2: Si SÍ está autenticado, muestra Saludo y Botón de Salir */}
              {isAuthenticated && user && (
                <>
                  <li className="nav-item d-flex align-items-center">
                    {/* Muestra el nombre del usuario si está disponible */}
                    <span className="nav-link fw-semibold">
                      Hola, {user.nombre || "Usuario"}
                    </span>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link fw-semibold px-3" to="/minegocio">
                      <button
                        className="btn btn-danger ms-lg-3"
                        onClick={logout} // Llama a la función logout del hook
                      >
                        Salir
                      </button>
                    </Link>
                  </li>
                </>
              )}
            </ul>
          </div>
        </div>
      </nav>
    </header>
  );
};

export default Header;
