package com.victorlicht.novenabackend.repositories;

import com.victorlicht.novenabackend.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
}
