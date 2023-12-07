package com.victorlicht.novenabackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private final String role = "ADMIN";

    @Column(nullable = false, unique = true)
    private String phoneNumber;
}
