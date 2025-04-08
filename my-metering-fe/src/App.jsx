import Footer from "./components/Footer";
import NavBarMetering from "./components/NavBarMetering";
import "./App.css";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Logout from "./components/Logout";
import Login from "./components/Login";
import ProtectedRoute from "./components/ProtectedRoute";
import Dashboard from "./components/Dashboard";


function App() {
  return (
    <>
      <BrowserRouter>
        <NavBarMetering />
        <Routes>
          <Route path="/" element={<Navigate to="/login" replace />} />
          <Route path="/login" element={<Login />} />
          <Route path="/logout" element={<Logout />} />

          <Route element={<ProtectedRoute />}>
            <Route path="/dashboard" element={<Dashboard />} />
          </Route>

          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
        <Footer />
      </BrowserRouter>
    </>
  );
}

export default App;
