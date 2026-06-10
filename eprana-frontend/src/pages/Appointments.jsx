import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import API from "../api/axios";
import toast from "react-hot-toast";

const Appointments = () => {

  const [appointments, setAppointments] = useState([]);

const [patients, setPatients] = useState([]);

const [doctors, setDoctors] = useState([]);

const role =
  localStorage.getItem("role");

const isAdmin =
  role === "ADMIN";

const isDoctor =
  role === "DOCTOR";

const isPatient =
  role === "PATIENT";

const [form, setForm] = useState({
  patientId: "",
  doctorId: "",
  appointmentTime: ""
});

  useEffect(() => {

  fetchAppointments();

  fetchPatients();

  fetchDoctors();

}, []);

  const fetchAppointments = async () => {

    try {

      const res = await API.get("/appointments");

      setAppointments(res.data);

    } catch (err) {

      console.log(err);

      alert("Failed to load appointments");
    }
  };

const fetchPatients = async () => {

  try {

    const res =
      await API.get("/patients");

    setPatients(res.data);

  } catch (err) {

    console.error(err);

  }

};

const fetchDoctors = async () => {

  try {

    const res =
      await API.get("/doctors");

    setDoctors(res.data);

  } catch (err) {

    console.error(err);

  }

};

const handleChange = (e) => {

  setForm({
    ...form,
    [e.target.name]: e.target.value
  });

};

const createAppointment = async (e) => {

  e.preventDefault();

  try {

    await API.post(
      "/appointments",
      {
        ...form,
        patientId: Number(form.patientId),
        doctorId: Number(form.doctorId)
      }
    );

toast.success("Appointment Booked Successfully");
    fetchAppointments();

    setForm({
      patientId: "",
      doctorId: "",
      appointmentTime: ""
    });

  } catch (err) {

    console.error(err);

    alert(
      JSON.stringify(
        err.response?.data
      )
    );

  }

};

  return (
    <div>

      <Navbar />

      <div className="p-8">

       <div className="mb-8">

  <h1
    className="
    text-4xl
    font-bold
    mb-2
    "
  >
    Appointments
  </h1>

  <p
    className="
    text-gray-500
    "
  >
    Schedule and manage appointments
  </p>

</div>

<div
  className="
  bg-white
  p-6
  rounded-xl
  shadow
  mb-8
  "
>

  <h2
    className="
    text-2xl
    font-bold
    mb-4
    "
  >
    Book Appointment
  </h2>

  <form
    onSubmit={createAppointment}
    className="
    grid
    md:grid-cols-2
    gap-4
    "
  >

   {!isPatient && !isDoctor && (

  <select
    name="patientId"
    value={form.patientId}
    onChange={handleChange}
    className="
border
border-gray-300
p-3
rounded-lg
focus:outline-none
focus:ring-2
focus:ring-blue-500
"
  >

    <option value="">
      Select Patient
    </option>

    {patients.map((patient) => (

      <option
        key={patient.id}
        value={patient.id}
      >
        {patient.patientName}
      </option>

    ))}

  </select>

)}
    {!isDoctor && (

  <select
    name="doctorId"
    value={form.doctorId}
    onChange={handleChange}
    className="
border
border-gray-300
p-3
rounded-lg
focus:outline-none
focus:ring-2
focus:ring-blue-500
"
  >

    <option value="">
      Select Doctor
    </option>

    {doctors.map((doctor) => (

      <option
        key={doctor.id}
        value={doctor.id}
      >
        {doctor.doctorName}
      </option>

    ))}

  </select>

)}

    <input
      type="datetime-local"
      name="appointmentTime"
      value={form.appointmentTime}
      onChange={handleChange}
      className="
border
border-gray-300
p-3
rounded-lg
focus:outline-none
focus:ring-2
focus:ring-blue-500
"
    />

    <button
      type="submit"
      className="
      bg-blue-600
hover:bg-blue-700
transition
duration-200
      text-white
      p-2
      rounded
      "
    >
      Book Appointment
    </button>

  </form>

</div>

        <div className="grid grid-cols-2 gap-6">

          {appointments.map((appointment) => (

            <div
              key={appointment.id}
              className="bg-white p-6 rounded shadow"
            >

              <p>
                Status:
                {" "}
                {appointment.status}
              </p>

              <p>
                Time:
                {" "}
                {appointment.appointmentTime}
              </p>

            </div>
          ))}

        </div>

      </div>
    </div>
  );
};

export default Appointments;