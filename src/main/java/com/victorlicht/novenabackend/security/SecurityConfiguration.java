package com.victorlicht.novenabackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {


    private UserDetailsService userDetailsService;

    public void SecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/v1/doctors/admin/create", "/auth/login"))
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/home", "/").hasAnyRole("ADMIN", "PATIENT")
                        .requestMatchers("/api/v1/***").anonymous()
                        .requestMatchers("/auth/login").anonymous()
                        .requestMatchers("/api/v1/doctors/admin/create").anonymous()
                        .requestMatchers("/hello").anonymous()
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
