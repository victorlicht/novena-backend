package com.victorlicht.novenabackend.repositories;

import com.victorlicht.novenabackend.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository <Admin, String> {
    Admin findByUsername(String username);
}
