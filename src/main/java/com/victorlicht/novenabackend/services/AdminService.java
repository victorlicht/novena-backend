package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.models.Admin;

public interface AdminService {
    Admin findByUsername(String username);
}
