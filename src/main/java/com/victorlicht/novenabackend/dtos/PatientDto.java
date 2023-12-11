package com.victorlicht.novenabackend.dtos;

import lombok.Data;

import java.sql.Date;

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

}
