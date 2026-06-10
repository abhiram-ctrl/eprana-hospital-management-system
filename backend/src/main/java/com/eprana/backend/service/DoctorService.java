package com.eprana.backend.service;

import com.eprana.backend.dto.DoctorRequestDto;
import com.eprana.backend.dto.response.DoctorResponseDto;
import com.eprana.backend.entity.Doctor;
import com.eprana.backend.entity.User;
import com.eprana.backend.repository.DoctorRepository;
import com.eprana.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public DoctorService(
            DoctorRepository doctorRepository,
            UserRepository userRepository
    ) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    private DoctorResponseDto mapToResponse(
            Doctor doctor
    ) {

        DoctorResponseDto dto =
                new DoctorResponseDto();

        dto.setId(doctor.getId());

        dto.setSpecialization(
                doctor.getSpecialization()
        );

        dto.setExperience(
                doctor.getExperience()
        );

        dto.setQualification(
                doctor.getQualification()
        );

        dto.setConsultationFee(
                doctor.getConsultationFee()
        );

        dto.setDoctorName(
                doctor.getUser().getName()
        );

        return dto;
    }
    // Create Doctor
    public DoctorResponseDto createDoctor(
            DoctorRequestDto dto
    ) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Doctor doctor = new Doctor();

        doctor.setSpecialization(dto.getSpecialization());
        doctor.setExperience(dto.getExperience());
        doctor.setQualification(dto.getQualification());
        doctor.setConsultationFee(dto.getConsultationFee());

        doctor.setUser(user);

        Doctor savedDoctor=doctorRepository.save(doctor);
        return mapToResponse(savedDoctor);
    }

    // Get All Doctors
    public List<DoctorResponseDto> getAllDoctors() {

        return doctorRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public List<Doctor> searchDoctors(
            String specialization
    ) {

        return doctorRepository
                .findBySpecializationContainingIgnoreCase(
                        specialization
                );
    }
}