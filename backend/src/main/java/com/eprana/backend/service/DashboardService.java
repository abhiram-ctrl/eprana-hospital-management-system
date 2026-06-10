package com.eprana.backend.service;

import com.eprana.backend.dto.response.DashboardRecentResponseDto;
import com.eprana.backend.dto.response.DashboardStatsResponseDto;
import com.eprana.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DashboardService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicationReminderRepository reminderRepository;

    public DashboardService(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository,
            PrescriptionRepository prescriptionRepository,
            MedicationReminderRepository reminderRepository
    ) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.reminderRepository = reminderRepository;
    }

    public DashboardStatsResponseDto getStats() {

        DashboardStatsResponseDto dto =
                new DashboardStatsResponseDto();

        dto.setTotalPatients(
                patientRepository.count()
        );

        dto.setTotalDoctors(
                doctorRepository.count()
        );

        dto.setTotalAppointments(
                appointmentRepository.count()
        );

        dto.setTotalPrescriptions(
                prescriptionRepository.count()
        );

        dto.setTotalReminders(
                reminderRepository.count()
        );

        return dto;
    }

    public List<DashboardRecentResponseDto> getRecentActivity() {

        List<DashboardRecentResponseDto> activities =
                new ArrayList<>();

        activities.add(
                new DashboardRecentResponseDto(
                        "Patient management system available"
                )
        );

        activities.add(
                new DashboardRecentResponseDto(
                        "Doctor management system available"
                )
        );

        activities.add(
                new DashboardRecentResponseDto(
                        "Appointments booking system active"
                )
        );

        return activities;
    }

}