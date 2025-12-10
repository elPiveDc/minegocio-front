import React, { useState } from "react";
import AdminNav from "../components/admin/AdminNav";
import AdminFAQ from "../components/admin/AdminFAQ";
import AdminDocumentos from "../components/admin/AdminDocumentos";
import AdminConsultas from "../components/admin/AdminConsultas";

const AdminHome: React.FC = () => {
  const [section, setSection] = useState("faqs");

  const renderSection = () => {
    switch (section) {
      case "faqs":
        return <AdminFAQ />;
      case "documentos":
        return <AdminDocumentos />;
      case "consultas":
        return <AdminConsultas />;
      default:
        return <AdminFAQ />;
    }
  };

  return (
    <div className="container py-4 min-vh-100">
      <AdminNav section={section} setSection={setSection} />
      {renderSection()}
    </div>
  );
};

export default AdminHome;
