package com.example.carRental.dto;

import com.example.carRental.model.Vehicle;
import com.example.carRental.model.VehicleStatus;

import java.math.BigDecimal;

public record VehicleResponse(
        Long id,
        String make,
        String model,
        int year,
        String registrationNumber,
        BigDecimal dailyRate,
        VehicleStatus status
) {
    public static VehicleResponse from(Vehicle v) {
        return new VehicleResponse(v.getId(), v.getMake(), v.getModel(), v.getYear(),
                v.getRegistrationNumber(), v.getDailyRate(), v.getStatus());
    }
}
