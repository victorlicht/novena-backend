package com.victorlicht.novenabackend.security.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthLoginForm {

    private String username;

    private String password;

    private String role;
}
