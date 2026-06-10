import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import API from "../api/axios";

const Dashboard = () => {

  const role = localStorage.getItem("role");

  const isAdmin = role === "ADMIN";
  const isDoctor = role === "DOCTOR";
  const isPatient = role === "PATIENT";

  const [stats, setStats] = useState({
    totalPatients: 0,
    totalDoctors: 0,
    totalAppointments: 0,
    totalPrescriptions: 0,
    totalReminders: 0,
  });

  const [activities, setActivities] = useState([]);

  useEffect(() => {
    loadStats();
    loadActivities();
  }, []);

  const loadStats = async () => {
    try {
      const res = await API.get("/dashboard/stats");
      setStats(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const loadActivities = async () => {
    try {
      const res = await API.get("/dashboard/recent");
      setActivities(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const cards = [
    {
      title: "Patients",
      subtitle: "Registered Patients",
      value: stats.totalPatients,
      color: "bg-blue-500",
    },
    {
      title: "Doctors",
      subtitle: "Available Doctors",
      value: stats.totalDoctors,
      color: "bg-green-500",
    },
    {
      title: "Appointments",
      subtitle: "Scheduled Appointments",
      value: stats.totalAppointments,
      color: "bg-orange-500",
    },
    {
      title: "Prescriptions",
      subtitle: "Issued Prescriptions",
      value: stats.totalPrescriptions,
      color: "bg-purple-500",
    },
    {
      title: "Reminders",
      subtitle: "Medication Reminders",
      value: stats.totalReminders,
      color: "bg-pink-500",
    },
  ];

  return (
    <div>
      <Navbar />

      <div className="p-8">

        <h1 className="text-4xl font-bold mb-4">
          E-Prana Dashboard
        </h1>

        <div
          className="
          bg-gradient-to-r
          from-blue-700
          to-cyan-500
          text-white
          p-8
          rounded-2xl
          mb-8
          shadow-lg
          "
        >
          <h1 className="text-4xl font-bold">
            Welcome to E-Prana
          </h1>

          <p className="mt-2 text-lg">
            Smart Hospital Management Platform
          </p>

          <p className="mt-2">
            Logged in as {role}
          </p>
        </div>

        <div className="grid md:grid-cols-3 gap-6">

          {cards.map((card) => (

            <div
              key={card.title}
              className="
              bg-white
              rounded-2xl
              shadow-md
              hover:shadow-xl
              transition
              duration-300
              p-6
              "
            >

              <div className="flex items-center gap-2">

                <div
                  className={`
                    w-4
                    h-4
                    rounded-full
                    ${card.color}
                  `}
                ></div>

                <h3 className="text-gray-700 font-medium">
                  {card.title}
                </h3>

              </div>

              <p className="text-sm text-gray-400 mt-1">
                {card.subtitle}
              </p>

              <p className="text-5xl font-bold mt-4">
                {card.value}
              </p>

            </div>

          ))}

        </div>

        <div
          className="
          bg-white
          rounded-2xl
          shadow
          p-6
          mt-8
          "
        >

          <h2
            className="
            text-2xl
            font-bold
            mb-4
            "
          >
            Recent Activity
          </h2>

          {activities.length === 0 ? (

            <p className="text-gray-500">
              No recent activity available
            </p>

          ) : (

            activities.map((activity, index) => (

              <div
                key={index}
                className="
                border-b
                py-3
                "
              >
                {activity.message}
              </div>

            ))

          )}

        </div>

        {isAdmin && (

          <div className="mt-10">

            <h2 className="text-2xl font-bold mb-4">
              Admin Actions
            </h2>

            <div className="flex gap-4">

              <a
                href="/patients"
                className="
                bg-blue-600
                hover:bg-blue-700
                transition
                duration-200
                text-white
                px-4
                py-2
                rounded
                "
              >
                Manage Patients
              </a>

              <a
                href="/doctors"
                className="
                bg-green-600
                hover:bg-green-700
                transition
                duration-200
                text-white
                px-4
                py-2
                rounded
                "
              >
                Manage Doctors
              </a>

            </div>

          </div>

        )}

        {isDoctor && (

          <div className="mt-10">

            <h2 className="text-2xl font-bold mb-4">
              Doctor Actions
            </h2>

            <a
              href="/prescriptions"
              className="
              bg-green-600
              hover:bg-green-700
              transition
              duration-200
              text-white
              px-4
              py-2
              rounded
              "
            >
              Create Prescription
            </a>

          </div>

        )}

        {isPatient && (

          <div className="mt-10">

            <h2 className="text-2xl font-bold mb-4">
              Patient Actions
            </h2>

            <div className="flex gap-4">

              <a
                href="/appointments"
                className="
                bg-blue-600
                hover:bg-blue-700
                transition
                duration-200
                text-white
                px-4
                py-2
                rounded
                "
              >
                Book Appointment
              </a>

              <a
                href="/reminders"
                className="
                bg-purple-600
                hover:bg-purple-700
                transition
                duration-200
                text-white
                px-4
                py-2
                rounded
                "
              >
                View Reminders
              </a>

            </div>

          </div>

        )}

      </div>
    </div>
  );
};

export default Dashboard;