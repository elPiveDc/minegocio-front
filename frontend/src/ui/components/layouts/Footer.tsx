import React from "react";
import { Link } from "react-router-dom";

const Footer: React.FC = () => {
  return (
    <footer className="bg-dark text-white pt-5 pb-3">
      <div className="container text-center text-md-start">
        <div className="row">
          {/* Logo y descripción */}
          <div className="col-12 col-md-4 mb-4">
            <Link
              to="/"
              className="navbar-brand d-flex align-items-center justify-content-center justify-content-md-start text-decoration-none"
            >
              <img
                src="../../../public/img/image.png"
                alt="Logo MiNegocio"
                width={50}
                className="me-2"
              />
              <span className="fw-bold fs-4 text-primary mb-0">MiNegocio</span>
            </Link>
            <p className="text-light mt-3">
              Plataforma de gestión de Franquicia/Negocio. Simplifica la
              administración y centraliza tu operación desde un solo lugar.
            </p>
          </div>

          {/* Enlaces rápidos */}
          <div className="col-12 col-md-4 mb-4">
            <h6 className="text-uppercase fw-bold mb-3">Enlaces</h6>
            <ul className="list-unstyled">
              <li>
                <Link
                  to="/minegocio/libro-reclamaciones"
                  className="text-white text-decoration-none d-block mb-2"
                  style={{ transition: "all 0.3s ease" }}
                  onMouseOver={(e) => {
                    (e.currentTarget as HTMLAnchorElement).style.transform =
                      "translateX(5px)";
                    (e.currentTarget as HTMLAnchorElement).style.color =
                      "#0d6efd";
                  }}
                  onMouseOut={(e) => {
                    (e.currentTarget as HTMLAnchorElement).style.transform =
                      "translateX(0)";
                    (e.currentTarget as HTMLAnchorElement).style.color =
                      "white";
                  }}
                >
                  Libro de reclamaciones
                </Link>
              </li>
              <li>
                <Link
                  to="/minegocio/documentos/terminos"
                  className="text-white text-decoration-none d-block mb-2"
                  style={{ transition: "all 0.3s ease" }}
                  onMouseOver={(e) => {
                    (e.currentTarget as HTMLAnchorElement).style.transform =
                      "translateX(5px)";
                    (e.currentTarget as HTMLAnchorElement).style.color =
                      "#0d6efd";
                  }}
                  onMouseOut={(e) => {
                    (e.currentTarget as HTMLAnchorElement).style.transform =
                      "translateX(0)";
                    (e.currentTarget as HTMLAnchorElement).style.color =
                      "white";
                  }}
                >
                  Términos y condiciones
                </Link>
              </li>
              <li>
                <Link
                  to="/minegocio/documentos/privacidad"
                  className="text-white text-decoration-none d-block mb-2"
                  style={{ transition: "all 0.3s ease" }}
                  onMouseOver={(e) => {
                    (e.currentTarget as HTMLAnchorElement).style.transform =
                      "translateX(5px)";
                    (e.currentTarget as HTMLAnchorElement).style.color =
                      "#0d6efd";
                  }}
                  onMouseOut={(e) => {
                    (e.currentTarget as HTMLAnchorElement).style.transform =
                      "translateX(0)";
                    (e.currentTarget as HTMLAnchorElement).style.color =
                      "white";
                  }}
                >
                  Política de privacidad
                </Link>
              </li>
              <li>
                <Link
                  to="/minegocio/preguntas"
                  className="text-white text-decoration-none d-block"
                  style={{ transition: "all 0.3s ease" }}
                  onMouseOver={(e) => {
                    (e.currentTarget as HTMLAnchorElement).style.transform =
                      "translateX(5px)";
                    (e.currentTarget as HTMLAnchorElement).style.color =
                      "#0d6efd";
                  }}
                  onMouseOut={(e) => {
                    (e.currentTarget as HTMLAnchorElement).style.transform =
                      "translateX(0)";
                    (e.currentTarget as HTMLAnchorElement).style.color =
                      "white";
                  }}
                >
                  Preguntas frecuentes
                </Link>
              </li>
            </ul>
          </div>

          {/* Contacto y redes */}
          <div className="col-12 col-md-4 mb-4">
            <h6 className="text-uppercase fw-bold mb-3">Contacto</h6>
            <p className="text-light mb-1">
              <i className="bi bi-envelope me-2"></i> soporte@minegocio.com
            </p>
            <p className="text-light mb-3">
              <i className="bi bi-phone me-2"></i> +51 999 999 999
            </p>
            <div>
              <a
                href="#"
                className="text-white me-3 fs-5 d-inline-block"
                style={{ transition: "transform 0.3s ease" }}
                onMouseOver={(e) => {
                  (e.currentTarget as HTMLElement).style.transform =
                    "scale(1.2)";
                }}
                onMouseOut={(e) => {
                  (e.currentTarget as HTMLElement).style.transform = "scale(1)";
                }}
              >
                <i className="bi bi-facebook"></i>
              </a>
              <a
                href="#"
                className="text-white me-3 fs-5 d-inline-block"
                style={{ transition: "transform 0.3s ease" }}
                onMouseOver={(e) => {
                  (e.currentTarget as HTMLElement).style.transform =
                    "scale(1.2)";
                }}
                onMouseOut={(e) => {
                  (e.currentTarget as HTMLElement).style.transform = "scale(1)";
                }}
              >
                <i className="bi bi-twitter"></i>
              </a>
              <a
                href="#"
                className="text-white me-3 fs-5 d-inline-block"
                style={{ transition: "transform 0.3s ease" }}
                onMouseOver={(e) => {
                  (e.currentTarget as HTMLElement).style.transform =
                    "scale(1.2)";
                }}
                onMouseOut={(e) => {
                  (e.currentTarget as HTMLElement).style.transform = "scale(1)";
                }}
              >
                <i className="bi bi-instagram"></i>
              </a>
              <a
                href="#"
                className="text-white fs-5 d-inline-block"
                style={{ transition: "transform 0.3s ease" }}
                onMouseOver={(e) => {
                  (e.currentTarget as HTMLElement).style.transform =
                    "scale(1.2)";
                }}
                onMouseOut={(e) => {
                  (e.currentTarget as HTMLElement).style.transform = "scale(1)";
                }}
              >
                <i className="bi bi-linkedin"></i>
              </a>
            </div>
          </div>
        </div>

        {/* Línea divisoria */}
        <hr className="border-secondary" />

        {/* Derechos */}
        <div className="text-center">
          <p className="mb-0 text-muted">
            &copy; 2025 <strong>MiNegocio</strong>. Todos los derechos
            reservados.
          </p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
