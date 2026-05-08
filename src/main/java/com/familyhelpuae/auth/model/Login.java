package com.familyhelpuae.auth.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Login {

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
    
}