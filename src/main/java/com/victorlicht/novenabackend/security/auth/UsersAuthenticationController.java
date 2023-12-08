package com.victorlicht.novenabackend.security.auth;

import com.victorlicht.novenabackend.services.AdminServiceImpl;
import com.victorlicht.novenabackend.services.DoctorServiceImpl;
import com.victorlicht.novenabackend.services.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UsersAuthenticationController {

    private final PasswordEncoder passwordEncoder;

    private final PatientServiceImpl patientService;

    private final DoctorServiceImpl doctorService;

    private final AdminServiceImpl adminService;

    @Autowired
    public UsersAuthenticationController(PasswordEncoder passwordEncoder, PatientServiceImpl patientService,
                                         DoctorServiceImpl doctorService, AdminServiceImpl adminService) {
        this.passwordEncoder = passwordEncoder;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticatePatient(@RequestBody AuthLoginForm authLoginForm) {
        UserDetails userDetails = null;
        try {
            if ("ADMIN".equals(authLoginForm.getRole())) {
                userDetails = adminService.loadUserByUsername(authLoginForm.getUsername());
            } else if ("DOCTOR".equals(authLoginForm.getRole())) {
                userDetails = doctorService.loadUserByUsername(authLoginForm.getUsername());
            } else if ("PATIENT".equals(authLoginForm.getRole())){
                userDetails = patientService.loadUserByUsername(authLoginForm.getUsername());
            }

            if (userDetails != null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority(authLoginForm.getRole()))) {
                if (passwordEncoder.matches(authLoginForm.getPassword(), userDetails.getPassword())) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String role = userDetails.getAuthorities().iterator().next().getAuthority();
                    return new ResponseEntity<>("User signed-in successfully!. role:" + role, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Invalid username or password.", HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>("Invalid role.", HttpStatus.UNAUTHORIZED);
            }
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Authentication failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
