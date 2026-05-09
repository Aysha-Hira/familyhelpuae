package com.familyhelpuae.auth.controller;

import org.springframework.stereotype.Controller;

import com.familyhelpuae.auth.service.AuthService;

@Controller

public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // useless for now

}
