package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.dtos.PatientDto;
import com.victorlicht.novenabackend.models.Patient;

import java.util.List;

public interface PatientService {

    PatientDto createPatientAccount(PatientDto patientDto);
    PatientDto updatePatientAccount(PatientDto patientDto, Patient patient);
    void deletePatientAccount(PatientDto patientDto);
    List<PatientDto> findAllPatients();

}
