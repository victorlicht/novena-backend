package com.victorlicht.novenabackend.controllers;

import com.victorlicht.novenabackend.dtos.PatientDto;
import com.victorlicht.novenabackend.mapper.PatientMapper;
import com.victorlicht.novenabackend.models.Patient;
import com.victorlicht.novenabackend.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/patients")
public class PatientController {
    //TODO: Patient(Admin Control) Create, List & Filter, Update, Search, Delete

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
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

    @PostMapping("/admin/create")
    public ResponseEntity<?> createPatientAccount(@Validated @RequestBody PatientDto patientDto) {
        try {
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
    public ResponseEntity<?> updatePatientAccount(@PathVariable String username, @Validated @RequestBody PatientDto patientDto) {
        try {
            PatientDto existingPatient = patientService.findByUsername(username);

            if (existingPatient != null) {
                Patient updatedPatient = PatientMapper.toEntity(patientDto);
                updatedPatient.setUsername(existingPatient.getUsername());
                PatientDto updatedDto = patientService.updatePatientAccount(patientDto, updatedPatient);
                return ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .body(updatedDto);
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
            PatientDto existingPatient = patientService.findByUsername(username);

            if (existingPatient != null) {
                patientService.deletePatientAccount(existingPatient);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(existingPatient.getFirstName() + " " + existingPatient.getLastName() + " Has Been Successfully Deleted");
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
}
