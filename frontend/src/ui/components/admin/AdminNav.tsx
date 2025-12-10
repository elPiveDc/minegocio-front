import React from "react";
import { Link } from "react-router-dom";

interface AdminNavProps {
  section: string;
  setSection: (section: string) => void;
}

const AdminNav: React.FC<AdminNavProps> = ({ section, setSection }) => {
  const renderNavButton = (
    buttonSection: string,
    iconClass: string,
    label: string
  ) => (
    <button
      className={`btn btn-outline-primary me-2 ${
        section === buttonSection ? "active" : ""
      }`}
      onClick={() => setSection(buttonSection)}
    >
      {/* Icono de Bootstrap */}
      <i className={iconClass}></i> {label}
    </button>
  );

  return (
    <div className="d-flex justify-content-between align-items-center mb-4 mt-5">
      <h2>Panel de Administración</h2>

      <div className="d-flex align-items-center">
        {renderNavButton("faqs", "bi bi-question-circle", "Preguntas")}
        {renderNavButton("documentos", "bi bi-file-earmark", "Documentos")}
        <button
          className={`btn btn-outline-primary ${
            section === "consultas" ? "active" : ""
          }`}
          onClick={() => setSection("consultas")}
        >
          <i className="bi bi-journal-text"></i> Consultas
        </button>
        <Link to="/minegocio" className="btn btn-success ms-3">
          Regresar
        </Link>
      </div>
    </div>
  );
};

export default AdminNav;
