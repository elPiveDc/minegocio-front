import React from "react";

const FranquiciaProductos: React.FC = () => {
  return (
    <div className="animate__animated animate__fadeIn">
      <div className="col d-flex justify-content-center">
        <h2 className="fw-bold mb-3 text-primary">Productos</h2>
      </div>
      <p className="text-muted">
        Gestión general de productos disponibles en la franquicia.
      </p>

      {/* Gráfico */}
      <div className="card shadow-sm p-4 mb-4 rounded-4">
        <div className="col d-flex justify-content-center">
          <h5 className="fw-semibold mb-3">Resumen de Inventario</h5>
        </div>

        <div className="mx-auto" style={{ maxWidth: "220px" }}>
          <img
            src="https://quickchart.io/chart?c={type:'bar',data:{labels:['Laptop','Mouse','Monitor','Silla'],datasets:[{label:'Stock',data:[12,35,8,20]}]}}"
            alt="chart"
            className="img-fluid rounded"
          />
        </div>
      </div>

      {/* Tabla */}
      <div className="card shadow-sm p-4 rounded-4">
        <h5 className="fw-semibold mb-3">Listado de Productos</h5>

        <div className="table-responsive">
          <table className="table table-striped table-hover align-middle">
            <thead className="table-primary">
              <tr>
                <th>ID</th>
                <th>Producto</th>
                <th>Categoría</th>
                <th>Stock</th>
                <th>Precio</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>101</td>
                <td>Laptop Lenovo ThinkPad</td>
                <td>Electrónica</td>
                <td>12</td>
                <td>S/. 3,500.00</td>
              </tr>
              <tr>
                <td>102</td>
                <td>Mouse Logitech M185</td>
                <td>Accesorios</td>
                <td>35</td>
                <td>S/. 85.00</td>
              </tr>
              <tr>
                <td>103</td>
                <td>Monitor Samsung 24"</td>
                <td>Electrónica</td>
                <td>8</td>
                <td>S/. 650.00</td>
              </tr>
              <tr>
                <td>104</td>
                <td>Silla ergonómica</td>
                <td>Muebles</td>
                <td>20</td>
                <td>S/. 420.00</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default FranquiciaProductos;
