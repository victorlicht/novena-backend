package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.dtos.AppointmentDto;
import com.victorlicht.novenabackend.dtos.DoctorDto;
import com.victorlicht.novenabackend.models.Appointment;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment createAppointment(AppointmentDto appointmentDto);
    boolean isDoctorWorking(DoctorDto doctorDto, Date date);
    boolean isAppointmentAvailable(AppointmentDto appointmentDto);
    boolean confirmAppointment(AppointmentDto appointmentDto);
    List<Appointment> findAllAppointment();
    void deleteAppointment(Appointment appointment);
    Optional<Appointment> searchById(Long id);
}
