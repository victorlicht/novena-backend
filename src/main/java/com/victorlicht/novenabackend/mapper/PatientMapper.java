package com.victorlicht.novenabackend.mapper;

import com.victorlicht.novenabackend.dtos.PatientDto;
import com.victorlicht.novenabackend.models.Patient;

public class PatientMapper {
    public static PatientDto toDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.setUsername(patient.getUsername());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setDateOfBirth(patient.getDateOfBirth());
        patientDto.setAddress(patient.getAddress());
        patientDto.setPhoneNumber(patient.getPhoneNumber());
        patientDto.setHealthInsurance(patient.getHealthInsurance());
        return patientDto;
    }

    public static Patient toEntity(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.setUsername(patientDto.getUsername());
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setDateOfBirth(patientDto.getDateOfBirth());
        patient.setAddress(patientDto.getAddress());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setHealthInsurance(patientDto.getHealthInsurance());
        return patient;
    }

    public static void updateEntityFromDto(PatientDto patientDto, Patient patient) {
        if (patientDto.getUsername() != null) {
            patient.setUsername(patientDto.getUsername());
        }
        if (patientDto.getFirstName() != null) {
            patient.setFirstName(patientDto.getFirstName());
        }
        if (patientDto.getLastName() != null) {
            patient.setLastName(patientDto.getLastName());
        }
        if (patientDto.getDateOfBirth() != null) {
            patient.setDateOfBirth(patientDto.getDateOfBirth());
        }
        if (patientDto.getAddress() != null) {
            patient.setAddress(patientDto.getAddress());
        }
        if (patientDto.getPhoneNumber() != null) {
            patient.setPhoneNumber(patientDto.getPhoneNumber());
        }
        if (patientDto.getHealthInsurance() != null) {
            patient.setHealthInsurance(patientDto.getHealthInsurance());
        }
    }
}
