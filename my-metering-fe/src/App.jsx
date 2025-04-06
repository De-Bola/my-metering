import Footer from "./components/Footer";
import NavBarMetering from "./components/NavBarMetering";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
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
          <Route element={<ProtectedRoute />}>
            <Route path="/" element={<Dashboard />} />
          </Route>
          <Route path="/logout" element={<Logout />} />
          <Route path="/login" element={<Login />} />
        </Routes>
        <Footer />
      </BrowserRouter>
    </>
  );
}

export default App;
