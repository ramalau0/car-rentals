package com.example.carRental.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// No userId here on purpose: the renter is always the logged-in user
public record RentalRequest(
        @NotNull Long vehicleId,
        @NotNull @FutureOrPresent LocalDate startDate,
        @NotNull LocalDate endDate
) {
}
