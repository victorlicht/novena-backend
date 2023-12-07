package com.victorlicht.novenabackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean status; // Indicates whether the appointment is confirmed by the doctor. Default is false until confirmed.
    @JoinColumn(nullable = false)

    @Column(length = 1000)
    private String description;

    @NonNull
    @ManyToOne
    private Doctor doctor;

    @NonNull
    @ManyToOne
    private Patient patient;
}
