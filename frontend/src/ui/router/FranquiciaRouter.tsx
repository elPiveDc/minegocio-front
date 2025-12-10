import { Routes, Route, Navigate } from "react-router-dom";
import FranquiciaLayout from "../pages/Franquicia";
import FranquiciaHome from "../components/franquicias/FranquiciaHome";
import FranquiciaProductos from "../components/franquicias/FranquiciaProductos";
import FranquiciaVentas from "../components/franquicias/FranquiciaVentas";
import FranquiciaModulos from "../components/franquicias/FranquiciaModulos";

const FranquiciaRouter: React.FC = () => {
  return (
    <Routes>
      <Route path="/" element={<FranquiciaLayout />}>
        <Route index element={<FranquiciaHome />} />
        <Route path="productos" element={<FranquiciaProductos />} />
        <Route path="ventas" element={<FranquiciaVentas />} />
        <Route path="modulos" element={<FranquiciaModulos />} />
      </Route>

      <Route path="*" element={<Navigate to="/404" replace />} />
    </Routes>
  );
};

export default FranquiciaRouter;
