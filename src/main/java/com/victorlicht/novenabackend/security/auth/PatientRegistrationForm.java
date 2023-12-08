package com.victorlicht.novenabackend.security.auth;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class PatientRegistrationForm {
    private String username;

    private String password;

    private String confirmPassword;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String address;

    private String phoneNumber;

    private String healthInsurance;
}
