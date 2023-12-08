package com.victorlicht.novenabackend.controllers;

import com.victorlicht.novenabackend.dtos.PatientDto;
import com.victorlicht.novenabackend.models.Patient;
import com.victorlicht.novenabackend.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    //TODO: Patient(Admin Control) List & Filter
    //TODO: Patient(Patient) Register, Update, Show, Delete, Show All Doctors, Choose Appointment Day with Doctor
    //TODO: Cancel Appointment, ReminderBeforeAppointment
    //TODO: Can Login, Logout Register and Login
    private final PatientService patientService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PatientController(PatientService patientService, PasswordEncoder passwordEncoder) {
        this.patientService = patientService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<?> findAllPatients() {
        try {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(patientService.findAllPatients());
        }catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server Error, Try Later" + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> findByUsername(@RequestParam String username) {
        Patient existingPatient = patientService.findByUsername(username);

        if (existingPatient != null) {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(existingPatient);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Username not found" + username);
        }
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> createPatientAccount(@RequestBody PatientDto patientDto) {
        try {
            String hashedPassword = passwordEncoder.encode(patientDto.getPassword());
            patientDto.setPassword(hashedPassword);

            PatientDto createdPatient = patientService.createPatientAccount(patientDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdPatient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create patient account: " + e.getMessage());
        }
    }

    @PutMapping("/admin/update/{username}")
    public ResponseEntity<?> updatePatientAccount(@PathVariable String username, @RequestBody PatientDto patientDto) {
        try {
            Patient existingPatient = patientService.findByUsername(username);

            if (existingPatient != null) {
                PatientDto updatedPatientAccount = patientService
                        .updatePatientAccount(patientDto, existingPatient);
                return ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .body(updatedPatientAccount);
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Username not found" + username);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update patient account: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/delete/{username}")
    public ResponseEntity<?> deletePatientAccount(@PathVariable String username) {
        try {
            Patient existingPatient = patientService.findByUsername(username);
            if (existingPatient != null) {
                patientService.deletePatientAccount(existingPatient);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body("Deleted");
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Username not found: " + username);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update patient account: " + e.getMessage());
        }
    }
}
