import { useState, useContext } from "react";
import API from "../api/axios";
import { useNavigate, Link } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import toast from "react-hot-toast";
import { GoogleLogin } from "@react-oauth/google";
const Login = () => {

  const navigate = useNavigate();

  const { login } = useContext(AuthContext);

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const handleChange = (e) => {

    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {

    e.preventDefault();

    try {

      const res = await API.post(
        "/auth/login",
        form
      );

login(res.data.token);

localStorage.setItem(
  "role",
  res.data.role
);
      alert("Login Successful");

      navigate("/dashboard");

    } catch (err) {

      alert("Invalid Credentials");
    }
  };

  return (
    <div className="min-h-screen flex justify-center items-center">

      <form
        onSubmit={handleSubmit}
        className="bg-white p-8 rounded shadow w-96"
      >

        <h2 className="text-2xl font-bold mb-6">
          E-Prana Login
        </h2>

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

        <button
          className="bg-blue-600 text-white w-full p-2 rounded"
        >
          Login
        </button>
        

<div className="mt-4">

  <GoogleLogin
   onSuccess={async (credentialResponse) => {

  try {

    const response = await API.post(
      "/auth/google",
      {
        credential:
          credentialResponse.credential
      }
    );

    localStorage.setItem(
      "token",
      response.data.token
    );

    localStorage.setItem(
      "role",
      response.data.role
    );

    alert(
      "Google Login Success"
    );

    navigate("/dashboard");

  } catch (error) {

    console.error(error);

    alert(
      "Google Login Failed"
    );

  }

}}
    onError={() => {

      alert(
        "Google Login Failed"
      );

    }}
  />

</div>

        <p className="mt-4 text-center">
          No account?
          <Link
            to="/register"
            className="text-blue-600 ml-1"
          >
            Register
          </Link>
        </p>

      </form>
    </div>
  );
};

export default Login;