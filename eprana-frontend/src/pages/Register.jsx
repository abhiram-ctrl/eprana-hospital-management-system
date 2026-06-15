import { useState } from "react";
import API from "../api/axios";
import { useNavigate } from "react-router-dom";
import toast from "react-hot-toast";

const Register = () => {

  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    role: "PATIENT",
  });

  const handleChange = (e) => {

    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    const passwordRegex =
  /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/;

if (!passwordRegex.test(form.password)) {

  alert(
    "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character"
  );

  return;
}

    try {

      await API.post(
        "/auth/register",
        form
      );

      alert("Registration Successful");

      navigate("/");

    } catch (err) {

      alert("Registration Failed");
    }
  };

  return (
    <div className="min-h-screen flex justify-center items-center">

      <form
        onSubmit={handleSubmit}
        className="bg-white p-8 rounded shadow w-96"
      >

        <h2 className="text-2xl font-bold mb-6">
          Register
        </h2>

        <input
          type="text"
          name="name"
          placeholder="Name"
          className="w-full border p-2 mb-4"
          onChange={handleChange}
        />

        <input
          type="email"
          name="email"
          placeholder="Email"
          className="w-full border p-2 mb-4"
          onChange={handleChange}
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          className="w-full border p-2 mb-4"
          onChange={handleChange}
        />

<p className="text-sm text-gray-500 mb-4">
  Password must contain:
  8+ characters,
  uppercase,
  lowercase,
  number,
  special character
</p>

        <select
          name="role"
          className="w-full border p-2 mb-4"
          onChange={handleChange}
        >
          <option value="PATIENT">PATIENT</option>
          <option value="DOCTOR">DOCTOR</option>
        </select>

        <button
          className="bg-green-600 text-white w-full p-2 rounded"
        >
          Register
        </button>

      </form>
    </div>
  );
};

export default Register;