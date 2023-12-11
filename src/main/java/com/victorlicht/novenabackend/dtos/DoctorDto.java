package com.victorlicht.novenabackend.dtos;

import lombok.Data;

import java.sql.Date;

@Data
public class DoctorDto {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String phoneNumber;

}
