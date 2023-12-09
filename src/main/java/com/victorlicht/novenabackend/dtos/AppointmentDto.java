package com.victorlicht.novenabackend.dtos;

import lombok.Data;

import java.sql.Date;

@Data
public class AppointmentDto {

    private Long id;

    private Date date;

    private String description;

    private DoctorDto doctorDto;

    private PatientDto patientDto;
}
