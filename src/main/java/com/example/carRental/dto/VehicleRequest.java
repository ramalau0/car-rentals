package com.example.carRental.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

// No id or status here on purpose: the client must not be able to set them
public record VehicleRequest(
        @NotBlank String make,
        @NotBlank String model,
        @NotNull @Min(1900) @Max(2100) Integer year,
        @NotBlank String registrationNumber,
        @NotNull @DecimalMin("0.01") BigDecimal dailyRate
) {
}
