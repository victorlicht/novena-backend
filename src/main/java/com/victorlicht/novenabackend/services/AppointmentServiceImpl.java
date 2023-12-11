package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.models.Appointment;
import com.victorlicht.novenabackend.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
         return appointmentRepository.save(appointment);
    }


    @Override
    public List<Appointment> findAllAppointment() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }

    @Override
    public Optional<Appointment> searchById(Long id) {
        return appointmentRepository.findById(id);
    }
}
