package com.victorlicht.novenabackend.dtos;

import com.victorlicht.novenabackend.models.Appointment;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class PatientDto {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String address;

    private String phoneNumber;

    private String healthInsurance;

    private List<Appointment> appointments;
}
