package com.victorlicht.novenabackend.controllers;

import com.victorlicht.novenabackend.dtos.DoctorDto;
import com.victorlicht.novenabackend.models.Doctor;
import com.victorlicht.novenabackend.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    //TODO: (Admin Control) Create, Update, List & Filter, Delete
    //TODO: (Doctor) Show All Doctor(Has Appointment with him), Login(Just Admin Can Create Doctor Account Than
    //TODO: Doctor Complete to Fill All The Information and Yeah Account Created Successfully)
    //TODO: (Doctor) Update, Delete, Confirm Appointment or Decline it, Check for Doctor he consult
    //TODO: Can Login with just username

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<?> findAllDoctors() {
        try {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(doctorService.findAllDoctors());
        }catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server Error, Try Later" + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> findByUsername(@RequestParam String username) {
        Doctor existingDoctor = doctorService.findByUsername(username);

        if (existingDoctor != null) {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(existingDoctor);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Username not found" + username);
        }
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> createDoctorAccount(@RequestBody DoctorDto doctorDto) {
        try {
            DoctorDto createdDoctor = doctorService.createDoctorAccount(doctorDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdDoctor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create doctor account: " + e.getMessage());
        }
    }

    @PutMapping("/admin/update/{username}")
    public ResponseEntity<?> updateDoctorAccount(@PathVariable String username, @RequestBody DoctorDto doctorDto) {
        try {
            Doctor existingDoctor = doctorService.findByUsername(username);

            if (existingDoctor != null) {
                DoctorDto updatedDoctorAccount = doctorService
                        .updateDoctorAccount(doctorDto, existingDoctor);
                return ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .body(updatedDoctorAccount);
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
                    .body("Failed to update doctor account: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/delete/{username}")
    public ResponseEntity<?> deleteDoctorAccount(@PathVariable String username) {
        try {
            Doctor existingDoctor = doctorService.findByUsername(username);
            if (existingDoctor != null) {
                doctorService.deleteDoctorAccount(existingDoctor);
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
                    .body("Failed to update doctor account: " + e.getMessage());
        }
    }

}
