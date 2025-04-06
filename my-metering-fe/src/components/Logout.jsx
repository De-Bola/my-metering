import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Logout = () => {
  const navigate = useNavigate();
  useEffect(() => {
    localStorage.removeItem("jwt");
    localStorage.removeItem("customer");
    navigate("/login", { replace: true });
  }, [navigate]);

  return <div>Logging out...</div>;
};

export default Logout;