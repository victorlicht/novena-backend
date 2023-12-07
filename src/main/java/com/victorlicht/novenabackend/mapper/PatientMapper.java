package com.victorlicht.novenabackend.mapper;

import com.victorlicht.novenabackend.dtos.PatientDto;
import com.victorlicht.novenabackend.models.Patient;

import java.util.List;
import java.util.stream.Collectors;

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
        patientDto.setAppointments(patient.getAppointments());

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
        patient.setAppointments(patientDto.getAppointments());

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
        if (patientDto.getAppointments() != null) {
            patient.setAppointments(patientDto.getAppointments());
        }
    }

    public static List<PatientDto> findAllPatientsToDto(List<Patient> patients) {
        return patients.stream()
                .map(PatientMapper::toDto)
                .collect(Collectors.toList());
    }
}
