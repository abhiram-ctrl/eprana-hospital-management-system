import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import API from "../api/axios";
import toast from "react-hot-toast";
const Patients = () => {

  const [patients, setPatients] = useState([]);

const [users, setUsers] = useState([]);

  const [form, setForm] = useState({
  age: "",
  gender: "",
  bloodGroup: "",
  disease: "",
  userId: ""
});

const handleChange = (e) => {

  setForm({
    ...form,
    [e.target.name]: e.target.value,
  });

};

const createPatient = async (e) => {

  e.preventDefault();

  try {

    await API.post(
      "/patients",
      form
    );

toast.success("Patient Created Successfully");
    fetchPatients();

    setForm({
      age: "",
      gender: "",
      bloodGroup: "",
      disease: "",
      userId: ""
    });

  } catch (err) {

    console.error(err);

toast.error("Failed to create patient");
  }

};

  useEffect(() => {

  fetchPatients();

  fetchUsers();

}, []);

  const fetchPatients = async () => {

    try {

      const res = await API.get("/patients");

      setPatients(res.data);

    } catch (err) {

      console.log(err);

      alert("Failed to load patients");
    }
  };
  const fetchUsers = async () => {

  try {

    const res =
      await API.get("/users");

const patientUsers =
  res.data.filter(
    user => user.role === "PATIENT"
  );

setUsers(patientUsers);
  } catch (err) {

    console.error(err);

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
    Patients
  </h1>

  <p
    className="
    text-gray-500
    "
  >
    Manage hospital patients
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
    Add Patient
  </h2>

  <form
    onSubmit={createPatient}
    className="
    grid
    md:grid-cols-2
    gap-4
    "
  >

    <input
      name="age"
      placeholder="Age"
      value={form.age}
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
      name="gender"
      placeholder="Gender"
      value={form.gender}
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
      name="bloodGroup"
      placeholder="Blood Group"
      value={form.bloodGroup}
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
      name="disease"
      placeholder="Disease"
      value={form.disease}
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
  name="userId"
  value={form.userId}
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
    Select User
  </option>

  {users.map((user) => (

    <option
      key={user.id}
      value={user.id}
    >
      {user.name}
    </option>

  ))}

</select>

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
      Create Patient
    </button>

  </form>

</div>

        <div className="grid grid-cols-3 gap-6">

          {patients.map((patient) => (

            <div
              key={patient.id}
              className="bg-white p-6 rounded shadow"
            >

              <h2 className="text-xl font-bold mb-2">
                {patient.patientName}
              </h2>

              <p>
                Age:
                {" "}
                {patient.age}
              </p>

              <p>
                Gender:
                {" "}
                {patient.gender}
              </p>

              <p>
                Blood Group:
                {" "}
                {patient.bloodGroup}
              </p>

              <p>
                Disease:
                {" "}
                {patient.disease}
              </p>

            </div>
          ))}

        </div>

      </div>
    </div>
  );
};

export default Patients;