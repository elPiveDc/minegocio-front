import { Routes, Route, Navigate } from "react-router-dom";
import MinegocioRouter from "./MinegocioRouter";
import FranquiciaRouter from "./FranquiciaRouter";
import NotFoundPage from "../components/layouts/NotFoundPage";
import Pruebas from "../pages/Pruebas";
import AdminHome from "../pages/AdminHome";
import ProtectedRoute from "../components/admin/ProtectedRoute";

const AppRouter: React.FC = () => {
  return (
    <Routes>
      <Route path="/minegocio/*" element={<MinegocioRouter />} />

      <Route path="/franquicia/Chocotejas/*" element={<FranquiciaRouter />} />

      <Route
        path="/admin/"
        element={
          <ProtectedRoute requiredRole="ADMIN">
            <AdminHome />
          </ProtectedRoute>
        }
      />

      <Route path="/pruebas/*" element={<Pruebas />} />

      <Route path="/404" element={<NotFoundPage />} />

      <Route path="*" element={<Navigate to="/minegocio" replace />} />
    </Routes>
  );
};

export default AppRouter;
