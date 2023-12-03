package com.victorlicht.novenabackend.mapper;

import com.victorlicht.novenabackend.dtos.AppointmentDto;
import com.victorlicht.novenabackend.models.Appointment;
import com.victorlicht.novenabackend.models.Doctor;
import com.victorlicht.novenabackend.models.Patient;

public class AppointmentMapper {
    public static AppointmentDto toDto(Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setAppointmentDate(appointment.getAppointmentDate());
        if (appointment.getDoctor() != null) {
            appointmentDto.setDoctorId(appointment.getDoctor().getId());
        }
        if (appointment.getPatient() != null) {
            appointmentDto.setPatientId(appointment.getPatient().getId());
        }
        return appointmentDto;
    }

    public static Appointment toEntity(AppointmentDto appointmentDto, Doctor doctor, Patient patient) {
        Appointment appointment = new Appointment();
        appointment.setId(appointmentDto.getId());
        appointment.setAppointmentDate(appointmentDto.getAppointmentDate());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        return appointment;
    }
}
