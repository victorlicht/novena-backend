package com.victorlicht.novenabackend.security.auth;

import com.victorlicht.novenabackend.dtos.PatientDto;
import com.victorlicht.novenabackend.services.AdminServiceImpl;
import com.victorlicht.novenabackend.services.DoctorServiceImpl;
import com.victorlicht.novenabackend.services.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UsersAuthenticationController {

    private final PasswordEncoder passwordEncoder;

    private final PatientServiceImpl patientService;

    private final DoctorServiceImpl doctorService;

    private final AdminServiceImpl adminService;

    @Autowired
    public UsersAuthenticationController(PasswordEncoder passwordEncoder, PatientServiceImpl patientService,
                                         DoctorServiceImpl doctorService, AdminServiceImpl adminService) {
        this.passwordEncoder = passwordEncoder;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticatePatient(@RequestBody AuthLoginForm authLoginForm) {
        UserDetails userDetails = null;
        try {
            if ("ADMIN".equals(authLoginForm.getRole())) {
                userDetails = adminService.loadUserByUsername(authLoginForm.getUsername());
            } else if ("DOCTOR".equals(authLoginForm.getRole())) {
                userDetails = doctorService.loadUserByUsername(authLoginForm.getUsername());
            } else if ("PATIENT".equals(authLoginForm.getRole())){
                userDetails = patientService.loadUserByUsername(authLoginForm.getUsername());
            }

            if (userDetails != null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority(authLoginForm.getRole()))) {
                if (passwordEncoder.matches(authLoginForm.getPassword(), userDetails.getPassword())) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String role = userDetails.getAuthorities().iterator().next().getAuthority();
                    return new ResponseEntity<>("User signed-in successfully!. role:" + role, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Invalid username or password.", HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>("Invalid role.", HttpStatus.UNAUTHORIZED);
            }
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Authentication failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register/patient")
    public ResponseEntity<?> registerPatient(@RequestBody PatientRegistrationForm patientForm) {
        try {
            // Check if the username already exists
            if (patientService.findByUsername(patientForm.getUsername()) != null) {
                return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);
            }

            // Validate if passwords match
            if (!patientForm.getPassword().equals(patientForm.getConfirmPassword())) {
                return new ResponseEntity<>("Passwords do not match.", HttpStatus.BAD_REQUEST);
            }

            // Create a new Patient object from the registration form
            PatientDto patient = new PatientDto();
            patient.setUsername(patientForm.getUsername());
            patient.setPassword(passwordEncoder.encode(patientForm.getPassword()));
            patient.setFirstName(patientForm.getFirstName());
            patient.setLastName(patientForm.getLastName());
            patient.setDateOfBirth(patientForm.getDateOfBirth());
            patient.setAddress(patientForm.getAddress());
            patient.setPhoneNumber(patientForm.getPhoneNumber());
            patient.setHealthInsurance(patientForm.getHealthInsurance());

            // Save the patient
            PatientDto savedPatient = patientService.createPatientAccount(patient);

            // Return a success response with the saved patient details
            return new ResponseEntity<>("Patient registered successfully: " + savedPatient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
