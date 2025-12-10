const NotFoundPage = () => {
  return (
    <div className="d-flex flex-column align-items-center justify-content-center text-center vh-100 p-3">
      <h1 className="display-4 mb-3">404 - Página no encontrada</h1>
      <p className="lead mb-5">La página que buscas no existe.</p>

      <img
        src="../../../../public/gif/error.gif"
        alt="Error de página 404"
        width={300}
      />

      <a href="/" className="btn btn-primary mt-4">
        Volver a la página principal
      </a>
      <a href="http://localhost:5173/" className="btn btn-primary mt-4">
        Jugar mientras se arregla el Problema
      </a>
    </div>
  );
};

export default NotFoundPage;
