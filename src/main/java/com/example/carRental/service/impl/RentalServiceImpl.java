package com.example.carRental.service.impl;

import com.example.carRental.dto.RentalRequest;
import com.example.carRental.dto.RentalResponse;
import com.example.carRental.exception.BadRequestException;
import com.example.carRental.exception.ConflictException;
import com.example.carRental.exception.NotFoundException;
import com.example.carRental.model.*;
import com.example.carRental.repository.RentalRepository;
import com.example.carRental.repository.UserRepository;
import com.example.carRental.repository.VehicleRepository;
import com.example.carRental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Override
    public RentalResponse rent(Long callerId, RentalRequest request) {
        if (!request.endDate().isAfter(request.startDate())) {
            throw new BadRequestException("End date must be after start date");
        }

        Vehicle vehicle = vehicleRepository.findByIdForUpdate(request.vehicleId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));

        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new ConflictException("Vehicle is not available");
        }

        // the renter always comes from the login, never from the request body
        User user = userRepository.findById(callerId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        long days = ChronoUnit.DAYS.between(request.startDate(), request.endDate());
        BigDecimal totalCost = vehicle.getDailyRate().multiply(BigDecimal.valueOf(days));

        Rental rental = Rental.builder()
                .user(user)
                .vehicle(vehicle)
                .startDate(request.startDate())
                .endDate(request.endDate())
                .totalCost(totalCost)
                .status(RentalStatus.ACTIVE)
                .build();

        vehicle.setStatus(VehicleStatus.RENTED);
        vehicleRepository.save(vehicle);

        return RentalResponse.from(rentalRepository.save(rental));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalResponse> getRentalsByUser(Long userId, Long callerId, Role callerRole) {
        // IDOR check: a normal user may only see their own rentals
        if (callerRole != Role.ADMIN && !userId.equals(callerId)) {
            throw new AccessDeniedException("Not allowed to view another user's rentals");
        }
        return rentalRepository.findByUserId(userId).stream()
                .map(RentalResponse::from)
                .toList();
    }

    @Override
    public RentalResponse returnRental(Long rentalId, Long callerId, Role callerRole) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new NotFoundException("Rental not found"));

        // IDOR check: only the person who rented it (or an admin) can return it
        if (callerRole != Role.ADMIN && !rental.getUser().getId().equals(callerId)) {
            throw new AccessDeniedException("Not allowed to return another user's rental");
        }
        if (rental.getStatus() != RentalStatus.ACTIVE) {
            throw new ConflictException("Rental has already been returned");
        }

        rental.setStatus(RentalStatus.RETURNED);
        rental.getVehicle().setStatus(VehicleStatus.AVAILABLE);

        return RentalResponse.from(rentalRepository.save(rental));
    }
}
