package com.eprana.backend.service;

import com.eprana.backend.dto.PatientRequestDto;
import com.eprana.backend.dto.response.PatientResponseDto;
import com.eprana.backend.entity.Patient;
import com.eprana.backend.entity.User;
import com.eprana.backend.repository.PatientRepository;
import com.eprana.backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientService(
            PatientRepository patientRepository,
            UserRepository userRepository
    ) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    private PatientResponseDto mapToResponse(
            Patient patient
    ) {

        PatientResponseDto dto =
                new PatientResponseDto();

        dto.setId(patient.getId());

        dto.setAge(
                patient.getAge()
        );

        dto.setGender(
                patient.getGender()
        );

        dto.setBloodGroup(
                patient.getBloodGroup()
        );

        dto.setDisease(
                patient.getDisease()
        );

        dto.setPatientName(
                patient.getUser().getName()
        );

        return dto;
    }
    // Create Patient
    public PatientResponseDto createPatient(
            PatientRequestDto dto
    ) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Patient patient = new Patient();

        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setDisease(dto.getDisease());

        patient.setUser(user);

        Patient savedPatient= patientRepository.save(patient);
        return mapToResponse(savedPatient);
    }

    // Get All Patients
    public List<PatientResponseDto> getAllPatients() {

        return patientRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public Page<PatientResponseDto> getPatientsPaginated(
            Pageable pageable
    ) {

        return patientRepository.findAll(pageable).map(this::mapToResponse);
    }

    public List<PatientResponseDto> searchPatients(
            String disease
    ) {

        return patientRepository
                .findByDiseaseContainingIgnoreCase(
                        disease
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}