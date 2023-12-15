package com.victorlicht.novenabackend.controllers;

import com.victorlicht.novenabackend.models.Appointment;
import com.victorlicht.novenabackend.models.Doctor;
import com.victorlicht.novenabackend.models.Patient;
import com.victorlicht.novenabackend.services.AppointmentService;
import com.victorlicht.novenabackend.services.DoctorService;
import com.victorlicht.novenabackend.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
    // TODO: Delete Appointment, SetOne (Between Doctor & Patient) No Edit you want to edit delete set one new
    // Reminder For Patient (to Set one appointment there is a lot of logic hiding there
    // For example after the doctor set his shifts, one shift has start and end time and then we do this
    // you set appointment we check with the shifts if it's yes you are putting appointment in the place of waiting
    // then after this when doctor came to confirm it for example he get 10 for that day but this day he can just get four
    // how to check is shifts end by method checks in stack is there is any place to push my self there no so if it's full
    // he can't accept (and i guess i will build a method when the stack is end he set all at the same day cancel automatically)
    // a lot of logic for app seems simple


    private final AppointmentService appointmentService;

    private final DoctorService doctorService;

    private final PatientService patientService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<?> bookAppointment(@RequestBody Appointment appointment, @RequestParam String patientUsername,
                                             @RequestParam String doctorUsername) {
        try {
            Doctor doctor = doctorService.findByUsername(doctorUsername);
            Patient patient = patientService.findByUsername(patientUsername);
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            if (appointmentService.createAppointment(appointment) != null) {
                return ResponseEntity.ok("Appointment booked successfully. Waiting for confirmation.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to book appointment");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> findAllAppointments() {
        List<Appointment> appointments = appointmentService.findAllAppointment();
        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.searchById(id);
        if (appointment.isPresent()) {
            appointmentService.deleteAppointment(appointment.get());
            return ResponseEntity.ok("Appointment deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.searchById(id);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(appointment);
    }
}
