package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.models.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment createAppointment(Appointment appointment);
    List<Appointment> findAllAppointment();
    void deleteAppointment(Appointment appointment);
    Optional<Appointment> searchById(Long id);
}
