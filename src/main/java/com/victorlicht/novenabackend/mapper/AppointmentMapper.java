package com.victorlicht.novenabackend.mapper;

import com.victorlicht.novenabackend.dtos.AppointmentDto;
import com.victorlicht.novenabackend.models.Appointment;

import java.util.List;
import java.util.stream.Collectors;

public class AppointmentMapper {
    public static AppointmentDto toDto(Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setDescription(appointment.getDescription());
        appointmentDto.setDoctorDto(DoctorMapper.toDto(appointment.getDoctor()));
        appointmentDto.setPatientDto(PatientMapper.toDto(appointment.getPatient()));

        return appointmentDto;
    }

    public static Appointment toEntity(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        appointment.setId(appointmentDto.getId());
        appointment.setDate(appointmentDto.getDate());
        appointment.setDescription(appointmentDto.getDescription());
        appointment.setDoctor(DoctorMapper.toEntity(appointmentDto.getDoctorDto()));
        appointment.setPatient(PatientMapper.toEntity(appointmentDto.getPatientDto()));

        return appointment;
    }

    public static void updateEntityDescriptionFromDto(AppointmentDto appointmentDto, Appointment appointment) {
        if (appointmentDto.getDescription() != null) {
            appointment.setDescription(appointmentDto.getDescription());
        }
    }

    public static List<AppointmentDto> findAllAppointmentsToDto(List<Appointment> appointments) {
        return appointments.stream()
                .map(AppointmentMapper::toDto)
                .collect(Collectors.toList());
    }
}
