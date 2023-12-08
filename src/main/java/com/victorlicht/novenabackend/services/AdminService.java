package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.models.Admin;

import java.util.List;

public interface AdminService {
    Admin findByUsername(String username);
    Admin createAdmin(Admin admin);
    Admin updateAdmin(Admin admin);
    void deleteAdmin(Admin admin);
    List<Admin> findAllAdmins();
}
