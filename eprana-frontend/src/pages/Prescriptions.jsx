import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import API from "../api/axios";
import toast from "react-hot-toast";

const Prescriptions = () => {

  const [prescriptions, setPrescriptions] = useState([]);

  const [appointments, setAppointments] = useState([]);

const [form, setForm] = useState({
  medicineName: "",
  dosage: "",
  instructions: "",
  appointmentId: ""
});

  useEffect(() => {

    fetchPrescriptions();
    fetchAppointments();

  }, []);

  const fetchPrescriptions = async () => {

    try {

      const res = await API.get("/prescriptions");

      setPrescriptions(res.data);

    } catch (err) {

      console.log(err);

toast.error("Failed to load prescription");    }
  };

  const fetchAppointments = async () => {

  try {

    const res =
      await API.get("/appointments");

    setAppointments(res.data);

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

const createPrescription = async (e) => {

  e.preventDefault();

  try {

    await API.post(
      "/prescriptions",
      {
        ...form,
        appointmentId: Number(form.appointmentId)
      }
    );

toast.success("Prescription Created Successfully");
    fetchPrescriptions();

    setForm({
      medicineName: "",
      dosage: "",
      instructions: "",
      appointmentId: ""
    });

 } catch (err) {

  console.error(err);

  toast.error(
    "Prescription already exists for this appointment"
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
    Prescriptions
  </h1>

  <p
    className="
    text-gray-500
    "
  >
    Manage patient prescriptions
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
    Create Prescription
  </h2>

  <form
    onSubmit={createPrescription}
    className="
    grid
    md:grid-cols-2
    gap-4
    "
  >

    <input
      name="medicineName"
      placeholder="Medicine Name"
      value={form.medicineName}
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

    <input
      name="dosage"
      placeholder="Dosage"
      value={form.dosage}
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

    <input
      name="instructions"
      placeholder="Instructions"
      value={form.instructions}
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

    <select
      name="appointmentId"
      value={form.appointmentId}
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
        Select Appointment
      </option>

      {appointments.map((appointment) => (

        <option
          key={appointment.id}
          value={appointment.id}
        >
          Appointment #{appointment.id}
        </option>

      ))}

    </select>

    <button
      type="submit"
      className="
      bg-green-600
      hover:bg-green-700
      transition
      duration-200
      text-white
      p-2
      rounded
      "
    >
      Create Prescription
    </button>

  </form>

</div>

        <div className="grid grid-cols-2 gap-6">

          {prescriptions.map((prescription) => (

            <div
              key={prescription.id}
              className="bg-white p-6 rounded shadow"
            >

              <h2 className="text-xl font-bold mb-2">
                {prescription.medicineName}
              </h2>

              <p>
                Dosage:
                {" "}
                {prescription.dosage}
              </p>

              <p>
                Instructions:
                {" "}
                {prescription.instructions}
              </p>

            </div>
          ))}

        </div>

      </div>
    </div>
  );
};

export default Prescriptions;