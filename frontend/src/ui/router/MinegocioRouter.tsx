import { Routes, Route, Navigate } from "react-router-dom";
import Home from "../pages/Home";
import Login from "../components/home/Login";
import Register from "../components/home/Register";
import Documentos from "../pages/pageshome/Documentos";
import DashboardLayout from "../pages/pageshome/DashboardLayout";
import Terminos from "../components/home/documento/Terminos";
import Privacidad from "../components/home/documento/Privacidad";
import LibroReclamaciones from "../components/home/LibroReclamaciones";
import Preguntas from "../components/home/Preguntas";
import ContenidoInicialHome from "../components/home/ContenidoInicialHome";
import UsuarioMe from "../components/home/usuario/UsuarioMe";
import UsuarioInvitaciones from "../components/home/usuario/UsuarioInvitaciones";
import UsuarioRegistrarFranquicia from "../components/home/usuario/UsuarioRegistrarFranquicia";

const MinegocioRouter: React.FC = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />}>
        <Route index element={<ContenidoInicialHome />} />

        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />

        <Route path="libro-reclamaciones" element={<LibroReclamaciones />} />
        <Route path="preguntas" element={<Preguntas />} />

        <Route path="documentos" element={<Documentos />}>
          <Route index element={<Terminos />} />
          <Route path="terminos" element={<Terminos />} />
          <Route path="privacidad" element={<Privacidad />} />
          <Route path="" element={<Navigate to="/404" replace />} />
        </Route>

        <Route path="dashboard" element={<DashboardLayout />}>
          <Route index element={<Navigate to="usuario" replace />} />
          <Route path="usuario" element={<UsuarioMe />} />
          <Route path="invitaciones" element={<UsuarioInvitaciones />} />
          <Route
            path="gestiona-franquicia"
            element={<UsuarioRegistrarFranquicia />}
          />
        </Route>
        <Route path="*" element={<Navigate to="/404" replace />} />
      </Route>
    </Routes>
  );
};

export default MinegocioRouter;
