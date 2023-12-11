package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.dtos.DoctorDto;
import com.victorlicht.novenabackend.models.Doctor;
import com.victorlicht.novenabackend.models.Shift;

import java.util.List;

public interface DoctorService {
    DoctorDto createDoctorAccount(DoctorDto doctorDto);
    DoctorDto updateDoctorAccount(DoctorDto doctorDto, Doctor doctor);
    void deleteDoctorAccount(Doctor doctor);
    List<DoctorDto> findAllDoctors();
    Doctor findByUsername(String username);
    List<Shift> listShifts();
    Shift createShift(Shift shift);
    void deleteShift(Shift shift);
}
