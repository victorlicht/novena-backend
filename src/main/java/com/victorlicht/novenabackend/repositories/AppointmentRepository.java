package com.victorlicht.novenabackend.repositories;

import com.victorlicht.novenabackend.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @NonNull
    Optional<Appointment> findById(@NonNull Long id);
}
