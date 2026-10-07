package com.example.carRental.dto;

import com.example.carRental.model.Rental;
import com.example.carRental.model.RentalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RentalResponse(
        Long id,
        Long userId,
        Long vehicleId,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal totalCost,
        RentalStatus status
) {
    public static RentalResponse from(Rental r) {
        return new RentalResponse(r.getId(), r.getUser().getId(), r.getVehicle().getId(),
                r.getStartDate(), r.getEndDate(), r.getTotalCost(), r.getStatus());
    }
}
