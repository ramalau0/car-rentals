package com.example.carRental.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank(message = "Full name is required")
        @Size(max = 100) String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format") String email,

        @Size(max = 30) String phone, // optional

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters") String password
) {}