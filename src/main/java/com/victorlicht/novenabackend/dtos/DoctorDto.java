package com.victorlicht.novenabackend.dtos;

import com.victorlicht.novenabackend.models.Appointment;
import com.victorlicht.novenabackend.models.Shift;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class DoctorDto {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String phoneNumber;

    private List<Appointment> appointments;

    private List<Shift> shifts;
}
