package com.victorlicht.novenabackend.services;

import com.victorlicht.novenabackend.models.Admin;
import com.victorlicht.novenabackend.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService, UserDetailsService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdmin(Admin admin) {
       return adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Admin admin) {
        adminRepository.delete(admin);
    }

    @Override
    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin loadedAdmin = adminRepository.findByUsername(username);

        if (loadedAdmin == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        String password = loadedAdmin.getPassword();;
        String role = loadedAdmin.getRole();

        return new org.springframework.security.core.userdetails.User(
                username,
                password,
                true,
                true,
                true,
                true,
                Collections.singleton(new SimpleGrantedAuthority(role))
        );
    }
}
