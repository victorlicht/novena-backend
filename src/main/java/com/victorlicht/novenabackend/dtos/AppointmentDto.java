package com.victorlicht.novenabackend.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDto {

    private Long id;

    private LocalDate appointmentDate;

    private String doctorId;

    private String patientId;
}
