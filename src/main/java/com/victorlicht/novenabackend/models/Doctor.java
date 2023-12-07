package com.victorlicht.novenabackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Date dateOfBirth;

    private final String role = "DOCTOR";

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @NonNull
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Shift> shifts;
}
