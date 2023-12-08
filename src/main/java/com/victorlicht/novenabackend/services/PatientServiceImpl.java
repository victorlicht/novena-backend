package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.dtos.PatientDto;
import com.victorlicht.novenabackend.mapper.PatientMapper;
import com.victorlicht.novenabackend.models.Patient;
import com.victorlicht.novenabackend.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService, UserDetailsService {

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
    public void deletePatientAccount(Patient patient) {
        patientRepository.delete(patient);
    }

    @Override
    public List<PatientDto> findAllPatients() {
        return PatientMapper.findAllPatientsToDto(patientRepository.findAll());
    }

    @Override
    public Patient findByUsername(String username) {
        return patientRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patient loadedPatient = patientRepository.findByUsername(username);

        if (loadedPatient == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        String password = loadedPatient.getPassword();;
        String role = loadedPatient.getRole();

        return new org.springframework.security.core.userdetails.User(
                username,
                password,
                true,
                true,
                true,
                true,
                Collections.singleton(new SimpleGrantedAuthority(role))
        );
    }
}
