package com.familyhelpuae.auth.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Register {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "5\\d{8}", message = "Enter a valid UAE number")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    // create family or join existing
    private boolean createFamily;
    private String familyId; // only filled if joining existing

    // Add to Register.java
    private String relatedUserId; // if the person is already on the platform
    private String relatedName; // if not registered
    private String relatedEmail; // if not registered
    private String relationshipType; // "father", "mother", "brother", "sister" etc.
}