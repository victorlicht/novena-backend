package com.victorlicht.novenabackend.controllers;

import com.victorlicht.novenabackend.dtos.DoctorDto;
import com.victorlicht.novenabackend.models.Doctor;
import com.victorlicht.novenabackend.models.Shift;
import com.victorlicht.novenabackend.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    //TODO: (Admin Control) Create, Update, List & Filter, Delete
    //TODO: (Doctor) Show All Doctor(Has Appointment with him), Login(Just Admin Can Create Doctor Account Than
    //TODO: Doctor Complete to Fill All The Information and Yeah Account Created Successfully)
    //TODO: (Doctor) Update, Delete, Confirm Appointment or Decline it, Check for Doctor he consult
    //TODO: Can Login with just username

    private final DoctorService doctorService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorController(DoctorService doctorService, PasswordEncoder passwordEncoder) {
        this.doctorService = doctorService;
        this.passwordEncoder = passwordEncoder;
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
            String hashedPassword = passwordEncoder.encode(doctorDto.getPassword());
            doctorDto.setPassword(hashedPassword);

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

    @GetMapping("/{username}/shifts")
    public ResponseEntity<List<Shift>> listShifts(@PathVariable String username) {
        Doctor doctor = doctorService.findByUsername(username);
        if (doctor != null) {
            List<Shift> shifts = doctorService.listShifts();
            List<Shift> doctorShifts = null;
            for (Shift shift: shifts) {
                if (shift.getDoctor() == doctor)
                    if (shift.isAvailable())
                        doctorShifts.add(shift);
            }
            return new ResponseEntity<>(doctorShifts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{username}/shifts")
    public ResponseEntity<Shift> createShift(@PathVariable String username, @RequestBody Shift shift) {
        Doctor doctor = doctorService.findByUsername(username);
        if (doctor != null) {
            shift.setDoctor(doctor);
            Shift createdShift = doctorService.createShift(shift);
            return new ResponseEntity<>(createdShift, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{username}/shifts")
    public ResponseEntity<?> deleteShift(@PathVariable String username, @RequestBody Shift shift) {
        Doctor doctor = doctorService.findByUsername(username);
        if (doctor != null) {
            doctorService.deleteShift(shift);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
