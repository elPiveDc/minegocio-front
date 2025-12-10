import React from "react";
import { Outlet } from "react-router-dom";

const Documentos: React.FC = () => {
  return (
    <div className="flex-grow-1 container py-5 min-vh-100">
      <Outlet />
    </div>
  );
};

export default Documentos;
