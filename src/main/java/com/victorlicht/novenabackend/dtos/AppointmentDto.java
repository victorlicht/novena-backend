package com.victorlicht.novenabackend.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDto {

    private Long id;

    private LocalDate date;

    private boolean status;

    private String description;

    private DoctorDto doctorDto;

    private PatientDto patientDto;
}
