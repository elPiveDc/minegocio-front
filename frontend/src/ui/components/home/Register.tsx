import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuthContext } from "../../hooks/useAuthContext";

const Register: React.FC = () => {
  const [nombre, setNombre] = useState("");
  const [correo, setCorreo] = useState("");
  const [password, setPassword] = useState("");
  const [mostrarPassword, setMostrarPassword] = useState(false);

  const navigate = useNavigate();

  const { register, error, isLoading } = useAuthContext();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await register({ nombre, correo, password });
      navigate("/minegocio/login");
    } catch {
      console.log("Ni dios sabra que salio mal");
    }
  };

  const togglePassword = () => {
    setMostrarPassword((prev) => !prev);
  };

  return (
    <div className="container py-4 mt-3">
      <div className="row justify-content-center">
        <div className="col-12 col-sm-10 col-md-8 col-lg-6">
          <div className="card shadow-lg p-4 border-0 rounded-4 animate__animated animate__fadeIn">
            <div className="text-center mb-4">
              <i className="bi bi-person-circle display-4 text-primary"></i>
              <h3 className="fw-bold mt-2 fs-4 fs-md-3">Crear Cuenta</h3>
              <p className="text-muted small mb-0">Regístrate para comenzar</p>
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
                <label htmlFor="nombre" className="form-label">
                  Nombre
                </label>
                <input
                  type="text"
                  className="form-control form-control-lg"
                  id="nombre"
                  required
                  value={nombre}
                  onChange={(e) => setNombre(e.target.value)}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="correo" className="form-label">
                  Correo
                </label>
                <input
                  type="email"
                  className="form-control form-control-lg"
                  id="correo"
                  required
                  value={correo}
                  onChange={(e) => setCorreo(e.target.value)}
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
                    required
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
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
                {isLoading ? "Procesando..." : "Registrar"}
              </button>
            </form>

            <div className="text-center mt-4">
              <button
                className="btn btn-link text-decoration-none fw-semibold"
                onClick={() => navigate("/minegocio/login")}
              >
                <i className="bi bi-arrow-left-circle"></i> Ya tengo una cuenta
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;
