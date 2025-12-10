import React, { useEffect, useRef, useState } from "react";
import { useJuegoConPuntuaciones } from "../hooks/useJuegoConPuntuaciones";
import { useCanvasRenderer } from "../hooks/useCanvasRenderer";
import { useGameDependencies } from "../context/GameContext";

type Props = { ancho?: number; alto?: number };

export default function JuegoConPuntuaciones({
  ancho = 800,
  alto = 700,
}: Props) {
  const canvasRef = useRef<HTMLCanvasElement | null>(null);
  const [juegoIniciado, setJuegoIniciado] = useState(false);

  const { repo, gen } = useGameDependencies();

  const {
    estadoRef,
    puntuacion,
    gameOver,
    iniciar,
    reiniciar,
    puntuaciones,
    cargando,
    error,
  } = useJuegoConPuntuaciones(ancho, alto, 10, repo, gen);

  useCanvasRenderer(canvasRef, estadoRef, ancho, alto, juegoIniciado);

  useEffect(() => {
    if (!juegoIniciado) return;
    const t = setTimeout(() => canvasRef.current?.focus(), 0);
    return () => clearTimeout(t);
  }, [juegoIniciado]);
  return (
    <div className="container mt-4">
      {/* Header */}
      <header className="mb-2 text-center">
        <h3 className="display-6 fw-bold">Minegocio game</h3>

        <p className="lead text-muted">
          Dispara y elimina el malware que intenta atacar a tu negocio
        </p>
      </header>

      {/* FILA PRINCIPAL */}
      <div className="row g-4">
        {/* ======================== */}
        {/* 📘 Columna Izquierda */}
        {/* ======================== */}
        <div className="col-12 col-md-3 d-flex flex-column gap-3">
          <a href="http://localhost:5174/" className="btn btn-primary">
            volver
          </a>
          <div className="p-3 bg-light rounded shadow-sm">
            <h5 className="fw-bold">🎮 Controles</h5>
            <ul className="small mb-0">
              <li>⬆⬇⬅➡ Mover</li>
              <li>Espacio → Disparar</li>
              <li>ESC → Pausa</li>
            </ul>
          </div>

          <div className="p-3 bg-light rounded shadow-sm">
            <h5 className="fw-bold">⚡ Power-ups</h5>
            <p className="small mb-0">Mejoran daño y velocidad.</p>
          </div>

          <div className="p-3 bg-light rounded shadow-sm">
            <h5 className="fw-bold">💡 Tips</h5>
            <p className="small mb-0">Muévete constantemente.</p>
          </div>
        </div>

        {/* ======================== */}
        {/* 🎮 Columna Central (Juego) */}
        {/* ======================== */}
        <div className="col-12 col-md-6 text-center">
          <div className="position-relative d-inline-block w-100">
            {/* CANVAS RESPONSIVE */}
            <canvas
              ref={canvasRef}
              width={ancho}
              height={alto}
              className="border border-dark shadow-lg rounded bg-dark w-100"
              style={{ height: "auto" }}
            />

            {/* Botón inicio */}
            {!juegoIniciado && (
              <button
                className="btn btn-primary btn-lg position-absolute start-50 top-50 translate-middle"
                onClick={() => {
                  setJuegoIniciado(true);
                  iniciar();
                }}
              >
                🚀 Empezar Juego
              </button>
            )}

            {/* Overlay Game Over */}
            {gameOver && (
              <div className="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center justify-content-center bg-dark bg-opacity-75 rounded">
                <div className="bg-dark text-white p-4 rounded shadow-lg w-75">
                  <h2 className="text-center mb-2">
                    {gameOver.tipo === "derrota"
                      ? "💀 Game Over"
                      : "🏆 ¡Victoria!"}
                  </h2>
                  <p className="text-center">{gameOver.mensaje}</p>
                  <p className="fw-bold text-center mb-3">
                    Puntuación final: {puntuacion}
                  </p>
                  <div className="d-flex justify-content-center gap-3">
                    <button className="btn btn-success" onClick={reiniciar}>
                      Reiniciar
                    </button>
                    <button
                      className="btn btn-outline-light"
                      onClick={() => window.location.reload()}
                    >
                      Salir
                    </button>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>

        {/* ======================== */}
        {/* 🏆 Columna Derecha (Tabla) */}
        {/* ======================== */}
        <div className="col-12 col-md-3">
          <h3 className="fw-bold text-center mb-3">🏅 Top 5</h3>

          {cargando && <p className="text-center text-muted">Cargando...</p>}
          {error && <p className="text-center text-danger">Error: {error}</p>}

          {!cargando && !error && (
            <div className="table-responsive shadow-sm rounded">
              <table className="table table-striped table-hover text-center">
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Nombre</th>
                    <th>Puntuación</th>
                  </tr>
                </thead>

                <tbody>
                  {[...puntuaciones]
                    .sort((a, b) => b.valorPuntuacion - a.valorPuntuacion)
                    .slice(0, 5)
                    .map((p, i) => (
                      <tr key={i}>
                        <td>{i + 1}</td>
                        <td>{p.nombreUsuario}</td>
                        <td>{p.valorPuntuacion}</td>
                      </tr>
                    ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
