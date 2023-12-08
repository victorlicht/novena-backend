package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.dtos.DoctorDto;
import com.victorlicht.novenabackend.mapper.DoctorMapper;
import com.victorlicht.novenabackend.models.Doctor;
import com.victorlicht.novenabackend.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService, UserDetailsService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public DoctorDto createDoctorAccount(DoctorDto doctorDto) {
        return DoctorMapper.toDto(doctorRepository.save(DoctorMapper.toEntity(doctorDto)));
    }

    @Override
    public DoctorDto updateDoctorAccount(DoctorDto doctorDto, Doctor doctor) {
        DoctorMapper.updateEntityFromDto(doctorDto, doctor);
        return DoctorMapper.toDto(doctorRepository.save(doctor));
    }

    @Override
    public void deleteDoctorAccount(Doctor doctor) {
        doctorRepository.delete(doctor);
    }

    @Override
    public List<DoctorDto> findAllDoctors() {
        return DoctorMapper.findAllDoctorsToDto(doctorRepository.findAll());
    }

    @Override
    public Doctor findByUsername(String username) {
        return doctorRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor loadedDoctor = doctorRepository.findByUsername(username);

        if (loadedDoctor == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        String password = loadedDoctor.getPassword();;
        String role = loadedDoctor.getRole();

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
