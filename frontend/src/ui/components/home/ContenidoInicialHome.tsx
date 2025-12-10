// ContenidoInicialHome.tsx
import React from "react";
import { Link } from "react-router-dom";

const ContenidoInicialHome: React.FC = () => {
  return (
    <>
      <div className="container py-5 mt-3 ">
        <div className="text-center mb-5">
          <h1 className="display-4 fw-bold mb-3 animate__animated animate__fadeInDown">
            Bienvenido a <span className="text-primary">MiNegocio</span>
          </h1>
          <p className="lead mx-auto" style={{ maxWidth: 800 }}>
            Somos una Plataforma para crear y gestionar tu negocio de forma
            sencilla, segura y centralizada. Administra usuarios, bases de datos
            y más desde un solo lugar.
          </p>
        </div>

        <div className="row justify-content-center gy-4">
          <div className="col-12 col-md-6 col-lg-5">
            <div
              className="card shadow-lg p-4 text-center h-100 border-0 transition hover-shadow"
              style={{ transition: "transform 0.3s ease" }}
              onMouseOver={(e) =>
                (e.currentTarget.style.transform = "scale(1.03)")
              }
              onMouseOut={(e) => (e.currentTarget.style.transform = "scale(1)")}
            >
              <i className="bi bi-shop display-3 text-success mb-3"></i>
              <h5 className="fw-semibold">¿No tienes una Franquicia?</h5>
              <p>
                Crea una cuenta e inicia tu Franquicia o Negocio desde cero,
                configura tu usuario principal y conecta tu base de datos
                fácilmente.
              </p>
              <Link to="/minegocio/register" className="btn btn-success mt-2">
                Crear una Cuenta
              </Link>
            </div>
          </div>

          <div className="col-12 col-md-6 col-lg-5">
            <div
              className="card shadow-lg p-4 text-center h-100 border-0 transition hover-shadow"
              style={{ transition: "transform 0.3s ease" }}
              onMouseOver={(e) =>
                (e.currentTarget.style.transform = "scale(1.03)")
              }
              onMouseOut={(e) => (e.currentTarget.style.transform = "scale(1)")}
            >
              <i className="bi bi-person-check-fill display-3 text-primary mb-3"></i>
              <h5 className="fw-semibold">¿Ya tienes una Franquicia?</h5>
              <p>
                Ingresa con tu usuario, contraseña. Recibe una invitacion o crea
                tu Franquicia, depues podras añadir los modulos que deses para
                acceder a todas las funcionalidades.
              </p>
              <Link to="/minegocio/login" className="btn btn-primary mt-2">
                Iniciar Sesión
              </Link>
            </div>
          </div>
        </div>

        {/* Características */}
        <div className="text-center mt-5">
          <h4 className="fw-bold mb-4">¿Qué puedes hacer con MiNegocio?</h4>

          <div className="row gy-4">
            <div className="col-12 col-md-4">
              <div
                className="p-4 bg-white rounded-3 shadow-sm h-100 transition"
                style={{ transition: "transform 0.3s ease" }}
                onMouseOver={(e) =>
                  (e.currentTarget.style.transform = "translateY(-5px)")
                }
                onMouseOut={(e) =>
                  (e.currentTarget.style.transform = "translateY(0)")
                }
              >
                <i className="bi bi-person-plus-fill display-4 text-info"></i>
                <h6 className="mt-3 fw-semibold">Crear Usuarios</h6>
                <p>Agrega usuarios con distintos roles para tu franquicia.</p>
              </div>
            </div>

            <div className="col-12 col-md-4">
              <div
                className="p-4 bg-white rounded-3 shadow-sm h-100 transition"
                style={{ transition: "transform 0.3s ease" }}
                onMouseOver={(e) =>
                  (e.currentTarget.style.transform = "translateY(-5px)")
                }
                onMouseOut={(e) =>
                  (e.currentTarget.style.transform = "translateY(0)")
                }
              >
                <i className="bi bi-database-gear display-4 text-warning"></i>
                <h6 className="mt-3 fw-semibold">Configurar BD</h6>
                <p>Asocia y configura la base de datos de tu franquicia.</p>
              </div>
            </div>

            <div className="col-12 col-md-4">
              <div
                className="p-4 bg-white rounded-3 shadow-sm h-100 transition"
                style={{ transition: "transform 0.3s ease" }}
                onMouseOver={(e) =>
                  (e.currentTarget.style.transform = "translateY(-5px)")
                }
                onMouseOut={(e) =>
                  (e.currentTarget.style.transform = "translateY(0)")
                }
              >
                <i className="bi bi-bar-chart-line-fill display-4 text-danger"></i>
                <h6 className="mt-3 fw-semibold">Ver Dashboard</h6>
                <p>Consulta estadísticas, accesos y usuarios activos.</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default ContenidoInicialHome;
