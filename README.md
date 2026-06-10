# E-Prana - Hospital Management System

## Overview

E-Prana is a full-stack Hospital Management System developed using Spring Boot, React.js, PostgreSQL, JWT Authentication, and Role-Based Access Control (RBAC).

The application helps manage patients, doctors, appointments, prescriptions, and medication reminders through a secure web-based platform.

## Features

### Authentication & Security

* JWT Authentication
* Role-Based Access Control (ADMIN, DOCTOR, PATIENT)
* Protected APIs
* Secure Password Encryption using BCrypt

### Patient Management

* Create Patients
* View Patients
* Associate Patients with User Accounts

### Doctor Management

* Create Doctors
* View Doctors
* Associate Doctors with User Accounts

### Appointment Management

* Book Appointments
* View Scheduled Appointments

### Prescription Management

* Create Prescriptions
* Link Prescriptions to Appointments

### Medication Reminder System

* Create Medication Reminders
* Automatic Reminder Processing using Spring Scheduler
* Reminder Completion Tracking

### Dashboard

* Total Patients
* Total Doctors
* Total Appointments
* Total Prescriptions
* Total Reminders
* Recent Activity Feed

## Tech Stack

### Backend

* Java
* Spring Boot
* Spring Security
* JWT
* Spring Scheduler
* PostgreSQL

### Frontend

* React.js
* Axios
* React Router
* Tailwind CSS

### Database

* PostgreSQL

## Architecture

React Frontend

↓

Spring Boot REST APIs

↓

PostgreSQL Database

## Future Enhancements

* Email Notifications
* SMS Reminders
* File Uploads
* Deployment on Cloud Platforms
* Mobile Application Support
