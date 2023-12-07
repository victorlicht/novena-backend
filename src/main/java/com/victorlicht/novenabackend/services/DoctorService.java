package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.dtos.DoctorDto;
import com.victorlicht.novenabackend.models.Doctor;

import java.util.List;

public interface DoctorService {
    DoctorDto createDoctorAccount(DoctorDto doctorDto);
    DoctorDto updateDoctorAccount(DoctorDto doctorDto, Doctor doctor);
    void deleteDoctorAccount(Doctor doctor);
    List<DoctorDto> findAllDoctors();
    Doctor findByUsername(String username);
}
