package com.eprana.backend.service;

import com.eprana.backend.dto.PrescriptionRequestDto;
import com.eprana.backend.entity.Appointment;
import com.eprana.backend.entity.Prescription;
import com.eprana.backend.repository.AppointmentRepository;
import com.eprana.backend.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    private final AppointmentRepository appointmentRepository;

    public PrescriptionService(
            PrescriptionRepository prescriptionRepository,
            AppointmentRepository appointmentRepository
    ) {

        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // Create Prescription
    public Prescription createPrescription(
            PrescriptionRequestDto dto
    ) {

        Appointment appointment =
                appointmentRepository.findById(dto.getAppointmentId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Appointment not found"
                                ));

        Prescription prescription =
                new Prescription();

        prescription.setMedicineName(
                dto.getMedicineName()
        );

        prescription.setDosage(
                dto.getDosage()
        );

        prescription.setInstructions(
                dto.getInstructions()
        );

        prescription.setAppointment(
                appointment
        );

        return prescriptionRepository.save(
                prescription
        );
    }

    // Get All Prescriptions
    public List<Prescription> getAllPrescriptions() {

        return prescriptionRepository.findAll();
    }
}