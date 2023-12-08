package com.victorlicht.novenabackend.security.auth;

import com.victorlicht.novenabackend.models.Admin;
import com.victorlicht.novenabackend.models.Doctor;
import com.victorlicht.novenabackend.models.Patient;
import com.victorlicht.novenabackend.repositories.AdminRepository;
import com.victorlicht.novenabackend.repositories.DoctorRepository;
import com.victorlicht.novenabackend.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@Configuration
public class CustomUserDetailsService implements UserDetailsService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public CustomUserDetailsService(PatientRepository patientRepository, DoctorRepository doctorRepository, AdminRepository adminRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patient loadedPatient = patientRepository.findByUsername(username);
        Doctor loadedDoctor = doctorRepository.findByUsername(username);
        Admin loadedAdmin = adminRepository.findByUsername(username);

        if (loadedPatient == null && loadedDoctor == null && loadedAdmin == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        String password;
        String role;

        if (loadedPatient != null) {
            password = loadedPatient.getPassword();
            role = loadedPatient.getRole();
        } else if (loadedDoctor != null) {
            password = loadedDoctor.getPassword();
            role = loadedDoctor.getRole();
        } else{
            password = loadedAdmin.getPassword();
            role = loadedAdmin.getRole();
        }

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
