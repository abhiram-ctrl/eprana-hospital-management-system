import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import API from "../api/axios";
import toast from "react-hot-toast";

const Reminders = () => {

  const [reminders, setReminders] = useState([]);

  const [prescriptions, setPrescriptions] = useState([]);

const [form, setForm] = useState({
  medicineName: "",
  reminderTime: "",
  prescriptionId: ""
});

  useEffect(() => {

    fetchReminders();
    fetchPrescriptions();

  }, []);

  const fetchReminders = async () => {

    try {

      const res = await API.get("/reminders");

      setReminders(res.data);

    } catch (err) {

      console.log(err);

      alert("Failed to load reminders");
    }
  };

const fetchPrescriptions = async () => {

  try {

    const res =
      await API.get("/prescriptions");

    setPrescriptions(res.data);

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

const createReminder = async (e) => {

  e.preventDefault();

  try {

    await API.post(
      "/reminders",
      {
        ...form,
        prescriptionId: Number(
          form.prescriptionId
        )
      }
    );

toast.success("Reminder Created Successfully");
    fetchReminders();

    setForm({
      medicineName: "",
      reminderTime: "",
      prescriptionId: ""
    });

  } catch (err) {

    console.error(err);

    toast.error("Failed to create reminder");


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
    Medication Reminders
  </h1>

  <p
    className="
    text-gray-500
    "
  >
    Track medication schedules
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
    Create Reminder
  </h2>

  <form
    onSubmit={createReminder}
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
      type="datetime-local"
      name="reminderTime"
      value={form.reminderTime}
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
      name="prescriptionId"
      value={form.prescriptionId}
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
        Select Prescription
      </option>

      {prescriptions.map((prescription) => (

       <option
  key={prescription.id}
  value={prescription.id}
>
  Prescription #{prescription.id}
</option>

      ))}

    </select>

    <button
      type="submit"
      className="
      bg-purple-600
hover:bg-purple-700
transition
duration-200
      text-white
      p-2
      rounded
      "
    >
      Create Reminder
    </button>

  </form>

</div>

        <div className="grid grid-cols-2 gap-6">

          {reminders.map((reminder) => (

            <div
              key={reminder.id}
              className="bg-white p-6 rounded shadow"
            >

              <h2 className="text-xl font-bold mb-2">
                {reminder.medicineName}
              </h2>

              <p>
                Reminder Time:
                {" "}
                {reminder.reminderTime}
              </p>

              <p>
                Completed:
                {" "}
                {reminder.completed
                  ? "YES"
                  : "NO"}
              </p>

            </div>
          ))}

        </div>

      </div>
    </div>
  );
};

export default Reminders;