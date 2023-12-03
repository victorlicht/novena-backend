package com.victorlicht.novenabackend.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {

    private Long id;

    private LocalDateTime appointmentDateTime;

    private String doctorId;

    private String patientId;
}
