package com.victorlicht.novenabackend.repositories;

import com.victorlicht.novenabackend.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
    Doctor findByUsername(String username);
}
