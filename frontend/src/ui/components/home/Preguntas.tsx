import React from "react";

const Preguntas: React.FC = () => {
  return (
    <div className="container py-5 mt-3">
      <div className="text-center mb-5">
        <h2 className="fw-bold text-primary mb-3">Centro de Preguntas</h2>
        <p className="text-muted">
          Escribe tu pregunta y el sistema te mostrará coincidencias.
        </p>
      </div>

      <div className="d-flex justify-content-center mb-4">
        <input
          type="text"
          className="form-control w-50 me-2"
          placeholder="Escribe tu pregunta..."
        />
        <button className="btn btn-primary">Buscar</button>
      </div>

      {/* Resultados simulados */}
      <div className="accordion" id="faqAccordion">
        <div className="accordion-item">
          <h2 className="accordion-header">
            <button
              className="accordion-button"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#collapse1"
              aria-expanded="true"
              aria-controls="collapse1"
            >
              ¿Cómo funciona este sitio?
            </button>
          </h2>
          <div
            id="collapse1"
            className="accordion-collapse collapse show"
            data-bs-parent="#faqAccordion"
          >
            <div className="accordion-body">
              Aquí se explica cómo usar MiNegocio de manera general.
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Preguntas;
