package com.victorlicht.novenabackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doctor_id", "date", "available", "appointmentsPerDay"})
})
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean available;

    private int appointmentsPerDay;

    @ManyToOne
    private Doctor doctor;

}
