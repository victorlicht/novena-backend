package com.victorlicht.novenabackend.repositories;

import com.victorlicht.novenabackend.models.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository <Shift, Long> {
}
