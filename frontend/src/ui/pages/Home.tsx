import React from "react";
import { Outlet } from "react-router-dom";
import Header from "../components/layouts/Header";
import Footer from "../components/layouts/Footer";

const Home: React.FC = () => {
  return (
    <>
      <Header />
      <Outlet />
      <Footer />
    </>
  );
};

export default Home;
