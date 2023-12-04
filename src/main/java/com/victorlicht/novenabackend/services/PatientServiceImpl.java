package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.dtos.PatientDto;
import com.victorlicht.novenabackend.mapper.PatientMapper;
import com.victorlicht.novenabackend.models.Patient;
import com.victorlicht.novenabackend.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientDto createPatientAccount(PatientDto patientDto) {
        return PatientMapper.toDto(patientRepository.save(PatientMapper.toEntity(patientDto)));
    }

    @Override
    public PatientDto updatePatientAccount(PatientDto patientDto, Patient patient) {
        PatientMapper.updateEntityFromDto(patientDto, patient);
        return PatientMapper.toDto(patientRepository.save(patient));
    }

    @Override
    public void deletePatientAccount(PatientDto patientDto) {
        patientRepository.delete(PatientMapper.toEntity(patientDto));
    }

    @Override
    public List<PatientDto> findAllPatients() {
        return PatientMapper.findAllPatientsToDto(patientRepository.findAll());
    }

    @Override
    public PatientDto findByUsername(String username) {
        return PatientMapper.toDto(patientRepository.findByUsername(username));
    }

}
