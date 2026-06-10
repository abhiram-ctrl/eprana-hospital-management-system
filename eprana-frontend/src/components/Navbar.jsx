import { Link, useNavigate } from "react-router-dom";

const Navbar = () => {

const role =
  localStorage.getItem("role");


const isAdmin =
  role === "ADMIN";

const isDoctor =
  role === "DOCTOR";

const isPatient =
  role === "PATIENT";

  const navigate = useNavigate();

  const logout = () => {

    localStorage.removeItem("token");
localStorage.removeItem("role");

    navigate("/");
  };

  return (
<div
  className="
  bg-gradient-to-r
  from-blue-700
  to-blue-500
  text-white
  px-6
  py-4
  flex
  items-center
  gap-6
  shadow-lg
  "
>
  <h1 className="text-2xl font-bold">
  E-Prana
</h1>
      <Link to="/dashboard">Dashboard</Link>

{isAdmin && (
  <>
    <Link to="/patients">Patients</Link>
    <Link to="/doctors">Doctors</Link>
    <Link to="/appointments">Appointments</Link>
    <Link to="/prescriptions">Prescriptions</Link>
    <Link to="/reminders">Reminders</Link>
  </>
)}

{isDoctor && (
  <>
    <Link to="/appointments">Appointments</Link>
    <Link to="/prescriptions">Prescriptions</Link>
  </>
)}
{isPatient && (
  <>
   <Link to="/doctors">Doctors</Link>
    <Link to="/appointments">Appointments</Link>
    <Link to="/reminders">Reminders</Link>
  </>
)}

      <button
        onClick={logout}
        className="ml-auto bg-red-500 px-3 py-1 rounded"
      >
        Logout
      </button>
    </div>
  );
};

export default Navbar;