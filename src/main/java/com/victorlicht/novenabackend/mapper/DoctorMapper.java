package com.victorlicht.novenabackend.mapper;

import com.victorlicht.novenabackend.dtos.DoctorDto;
import com.victorlicht.novenabackend.models.Doctor;

public class DoctorMapper {
    public static DoctorDto toDto(Doctor doctor) {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setUsername(doctor.getUsername());
        doctorDto.setFirstName(doctor.getFirstName());
        doctorDto.setLastName(doctor.getLastName());
        doctorDto.setDateOfBirth(doctor.getDateOfBirth());
        doctorDto.setPhoneNumber(doctor.getPhoneNumber());
        return doctorDto;
    }

    public static Doctor toEntity(DoctorDto doctorDto) {
        Doctor doctor = new Doctor();
        doctor.setUsername(doctorDto.getUsername());
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
}
