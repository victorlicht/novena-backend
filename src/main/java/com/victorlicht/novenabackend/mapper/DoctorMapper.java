package com.victorlicht.novenabackend.mapper;

import com.victorlicht.novenabackend.dtos.DoctorDto;
import com.victorlicht.novenabackend.models.Doctor;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorMapper {
    public static DoctorDto toDto(Doctor doctor) {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setUsername(doctor.getUsername());
        doctorDto.setPassword(doctor.getPassword());
        doctorDto.setFirstName(doctor.getFirstName());
        doctorDto.setLastName(doctor.getLastName());
        doctorDto.setDateOfBirth(doctor.getDateOfBirth());
        doctorDto.setPhoneNumber(doctor.getPhoneNumber());

        return doctorDto;
    }

    public static Doctor toEntity(DoctorDto doctorDto) {
        Doctor doctor = new Doctor();
        doctor.setUsername(doctorDto.getUsername());
        doctor.setPassword(doctorDto.getPassword());
        doctor.setFirstName(doctorDto.getFirstName());
        doctor.setLastName(doctorDto.getLastName());
        doctor.setDateOfBirth(doctorDto.getDateOfBirth());
        doctor.setPhoneNumber(doctorDto.getPhoneNumber());

        return doctor;
    }

    public static void updateEntityFromDto(DoctorDto doctorDto, Doctor doctor) {
        if (doctorDto.getUsername() != null) {
            doctor.setUsername(doctorDto.getUsername());
        }
        if (doctorDto.getFirstName() != null) {
            doctor.setFirstName(doctorDto.getFirstName());
        }
        if (doctorDto.getLastName() != null) {
            doctor.setLastName(doctorDto.getLastName());
        }
        if (doctorDto.getDateOfBirth() != null) {
            doctor.setDateOfBirth(doctorDto.getDateOfBirth());
        }
        if (doctorDto.getPhoneNumber() != null) {
            doctor.setPhoneNumber(doctorDto.getPhoneNumber());
        }
    }

    public static List<DoctorDto> findAllDoctorsToDto(List<Doctor> doctors) {
        return doctors.stream()
                .map(DoctorMapper::toDto)
                .collect(Collectors.toList());
    }
}
