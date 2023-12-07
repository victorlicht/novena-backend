package com.victorlicht.novenabackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @Column(unique = true, nullable = false)
    private String username;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String address;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String healthInsurance;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
