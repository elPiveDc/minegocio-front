import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuthContext } from "../../hooks/useAuthContext";
import { hasBackendErrorMessage } from "../../../utils/TypeGuards";

const Login: React.FC = () => {
  const [usuario, setUsuario] = useState("");
  const [password, setPassword] = useState("");
  const [mostrarPassword, setMostrarPassword] = useState(false);

  const navigate = useNavigate();

  const { login, error, isLoading } = useAuthContext();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await login({
        correo: usuario,
        password,
      });

      navigate("/minegocio/dashboard");
    } catch (err) {
      if (hasBackendErrorMessage(err)) {
        console.error("Error backend:", err.response.data.message);
      }
    }
  };

  const togglePassword = () => setMostrarPassword(!mostrarPassword);

  return (
    <div className="container py-5 mt-3">
      <div className="row justify-content-center">
        <div className="col-12 col-sm-10 col-md-8 col-lg-6">
          <div className="card shadow-lg p-4 border-0 rounded-4 animate__animated animate__fadeIn">
            <div className="text-center mb-4">
              <i className="bi bi-person-circle display-4 text-primary"></i>
              <h3 className="fw-bold mt-2 fs-4 fs-md-3">Iniciar Sesión</h3>
              <p className="text-muted small mb-0">
                Accede a tu cuenta para continuar
              </p>
            </div>

            {error && (
              <div
                className="alert alert-danger text-center animate__animated animate__shakeX"
                role="alert"
              >
                <i className="bi bi-exclamation-triangle-fill me-2"></i>
                {error}
              </div>
            )}

            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="usuario" className="form-label">
                  Correo
                </label>
                <input
                  type="email"
                  className="form-control form-control-lg"
                  id="usuario"
                  value={usuario}
                  onChange={(e) => setUsuario(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="password" className="form-label">
                  Contraseña
                </label>
                <div className="input-group">
                  <input
                    type={mostrarPassword ? "text" : "password"}
                    className="form-control form-control-lg"
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                  <button
                    className="btn btn-outline-secondary"
                    type="button"
                    onClick={togglePassword}
                  >
                    <i
                      className={`bi ${
                        mostrarPassword ? "bi-eye-slash" : "bi-eye"
                      }`}
                    ></i>
                  </button>
                </div>
              </div>

              <button
                type="submit"
                className="btn btn-primary btn-lg w-100 d-flex align-items-center justify-content-center gap-2 shadow-sm"
                disabled={isLoading}
              >
                <i className="bi bi-box-arrow-in-right"></i>
                {isLoading ? "Cargando..." : "Ingresar"}
              </button>
            </form>

            <div className="text-center mt-4">
              <Link
                to="/minegocio"
                className="text-decoration-none fw-semibold"
              >
                <i className="bi bi-arrow-left-circle"></i> Volver al inicio
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
