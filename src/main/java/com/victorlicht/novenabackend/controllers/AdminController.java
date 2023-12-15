package com.victorlicht.novenabackend.controllers;

import com.victorlicht.novenabackend.models.Admin;
import com.victorlicht.novenabackend.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final AdminService adminService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(AdminService adminService, PasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<?> findAllAdmins() {
        try {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(adminService.findAllAdmins());
        }catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server Error, Try Later" + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findByUsername(@RequestParam String username) {
        Admin existingAdmin = adminService.findByUsername(username);

        if (existingAdmin != null) {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(existingAdmin);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Username not found" + username);
        }
    }

    @PostMapping
    public ResponseEntity<?> createAdminAccount(@RequestBody Admin admin) {
        try {
            String hashedPassword = passwordEncoder.encode(admin.getPassword());
            admin.setPassword(hashedPassword);

            Admin createdAdmin = adminService.createAdmin(admin);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(createdAdmin);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create admin account: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAdminAccount(@RequestParam String username, @RequestBody Admin admin) {
        try {
            Admin existingAdmin = adminService.findByUsername(username);

            if (existingAdmin != null) {
                Admin updatedAdmin = adminService.updateAdmin(admin);
                return ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .body(updatedAdmin);
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Username not found" + username);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update admin account: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAdminAccount(@RequestParam String username) {
        try {
            Admin existingAdmin = adminService.findByUsername(username);
            if (existingAdmin != null) {
                adminService.deleteAdmin(existingAdmin);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body("Deleted");
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Username not found: " + username);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update admin account: " + e.getMessage());
        }
    }
}
