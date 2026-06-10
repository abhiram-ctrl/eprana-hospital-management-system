import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import API from "../api/axios";
import toast from "react-hot-toast";

const Doctors = () => {

  const [doctors, setDoctors] = useState([]);

  const [users, setUsers] = useState([]);

const [form, setForm] = useState({
  specialization: "",
  experience: "",
  qualification: "",
  consultationFee: "",
  userId: ""
});

  useEffect(() => {

  fetchDoctors();

  fetchUsers();

}, []);

  const fetchDoctors = async () => {

    try {

      const res = await API.get("/doctors");

      setDoctors(res.data);

    } catch (err) {

      console.log(err);

toast.error("Failed to load doctor");    }
  };

const fetchUsers = async () => {

  try {

    const res =
      await API.get("/users");

    const doctorUsers =
      res.data.filter(
        user => user.role === "DOCTOR"
      );

    setUsers(doctorUsers);

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

const createDoctor = async (e) => {

  e.preventDefault();

  try {

    await API.post(
      "/doctors",
      form
    );

toast.success("Doctor Created Successfully");
    fetchDoctors();

    setForm({
      specialization: "",
      experience: "",
      qualification: "",
      consultationFee: "",
      userId: ""
    });

 } catch (err) {

  console.error(err);

  toast.error(
    "Doctor profile already exists"
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
    Doctors
  </h1>

  <p
    className="
    text-gray-500
    "
  >
    Manage hospital doctors
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
    Add Doctor
  </h2>

  <form
    onSubmit={createDoctor}
    className="
    grid
    md:grid-cols-2
    gap-4
    "
  >

    <input
      name="specialization"
      placeholder="Specialization"
      value={form.specialization}
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
      name="experience"
      placeholder="Experience"
      value={form.experience}
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
      name="qualification"
      placeholder="Qualification"
      value={form.qualification}
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
      name="consultationFee"
      placeholder="Consultation Fee"
      value={form.consultationFee}
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
        Select Doctor User
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
      bg-green-600
      hover:bg-green-700
      transition
      duration-200
      text-white
      p-2
      rounded
      "
    >
      Create Doctor
    </button>

  </form>

</div>

        <div className="grid grid-cols-3 gap-6">

          {doctors.map((doctor) => (

            <div
              key={doctor.id}
              className="bg-white p-6 rounded shadow"
            >

              <h2 className="text-xl font-bold mb-2">
                {doctor.doctorName}
              </h2>

              <p>
                Specialization:
                {" "}
                {doctor.specialization}
              </p>

              <p>
                Experience:
                {" "}
                {doctor.experience} years
              </p>

              <p>
                Qualification:
                {" "}
                {doctor.qualification}
              </p>

              <p>
                Fee:
                {" "}
                ₹{doctor.consultationFee}
              </p>

            </div>
          ))}

        </div>

      </div>
    </div>
  );
};

export default Doctors;