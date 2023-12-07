package com.victorlicht.novenabackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/home", "/").permitAll()
                        .requestMatchers("/hello").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).formLogin((form) -> form
                        .loginPage("/login").permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("12345678"))
                .roles("ADMIN", "PATIENT").build();
        UserDetails user2 = User.builder()
                .username("user2")
                .password(passwordEncoder().encode("12345678"))
                .roles("PATIENT").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}
