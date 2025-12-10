import React, { useEffect, useMemo } from "react";
import { usePublicos } from "../../../hooks/usePublicos";
import { base64ToBlobUrl } from "../../../../utils/base64";

const Privacidad: React.FC = () => {
  const { documento, loading, error, obtenerPoliticaPrivacidad } =
    usePublicos();

  useEffect(() => {
    obtenerPoliticaPrivacidad();
  }, [obtenerPoliticaPrivacidad]);

  const blobUrl = useMemo(() => {
    if (!documento) return undefined;

    return base64ToBlobUrl(documento.archivo, documento.tipoContenido);
  }, [documento]);

  return (
    <div className="text-center">
      <h2 className="fw-bold text-primary">Documento Política de Privacidad</h2>
      <p className="text-muted mb-4">
        Aquí puedes ver o descargar el documento oficial de Privacidad.
      </p>

      {loading && <p>Cargando documento...</p>}
      {error && <p className="text-danger">{error}</p>}

      {documento && blobUrl && (
        <>
          <div className="mb-4">
            <a
              href={blobUrl}
              target="_blank"
              rel="noopener noreferrer"
              className="btn btn-outline-primary me-2"
            >
              <i className="bi bi-eye"></i> Ver en línea
            </a>

            <a
              href={blobUrl}
              download={`${documento.slug || "privacidad"}.pdf`}
              className="btn btn-primary"
            >
              <i className="bi bi-download"></i> Descargar
            </a>
          </div>

          <div style={{ border: "1px solid #ddd", borderRadius: "8px" }}>
            <iframe
              src={blobUrl}
              style={{ width: "100%", height: "600px", border: "none" }}
              title="Política de Privacidad"
            ></iframe>
          </div>
        </>
      )}
    </div>
  );
};

export default Privacidad;
