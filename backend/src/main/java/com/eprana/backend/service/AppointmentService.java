package com.eprana.backend.service;

import com.eprana.backend.dto.AppointmentRequestDto;
import com.eprana.backend.dto.response.AppointmentResponseDto;
import com.eprana.backend.entity.Appointment;
import com.eprana.backend.entity.Doctor;
import com.eprana.backend.entity.Patient;
import com.eprana.backend.enums.AppointmentStatus;
import com.eprana.backend.repository.AppointmentRepository;
import com.eprana.backend.repository.DoctorRepository;
import com.eprana.backend.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final PatientRepository patientRepository;

    private final DoctorRepository doctorRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository
    ) {

        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    private AppointmentResponseDto mapToResponse(
            Appointment appointment
    ) {

        AppointmentResponseDto dto =
                new AppointmentResponseDto();

        dto.setId(
                appointment.getId()
        );

        dto.setAppointmentTime(
                appointment.getAppointmentTime()
        );

        dto.setStatus(
                appointment.getStatus()
        );

        dto.setPatientName(
                appointment.getPatient()
                        .getUser()
                        .getName()
        );

        dto.setDoctorName(
                appointment.getDoctor()
                        .getUser()
                        .getName()
        );

        return dto;
    }

    // Create Appointment
    public AppointmentResponseDto createAppointment(
            AppointmentRequestDto dto
    ) {

        Patient patient = patientRepository
                .findById(dto.getPatientId())
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepository
                .findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        Appointment appointment =
                new Appointment();

        appointment.setAppointmentTime(
                dto.getAppointmentTime()
        );

        appointment.setStatus(
                AppointmentStatus.BOOKED
        );

        appointment.setPatient(
                patient
        );

        appointment.setDoctor(
                doctor
        );

        Appointment savedAppointment =
                appointmentRepository.save(
                        appointment
                );

        return mapToResponse(
                savedAppointment
        );
    }

    // Get All Appointments
    public List<AppointmentResponseDto>
    getAllAppointments() {

        return appointmentRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}