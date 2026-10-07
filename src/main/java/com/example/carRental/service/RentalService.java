package com.example.carRental.service;

import com.example.carRental.dto.RentalRequest;
import com.example.carRental.dto.RentalResponse;
import com.example.carRental.model.Role;

import java.util.List;

public interface RentalService {

    RentalResponse rent(Long callerId, RentalRequest request);

    List<RentalResponse> getRentalsByUser(Long userId, Long callerId, Role callerRole);

    RentalResponse returnRental(Long rentalId, Long callerId, Role callerRole);
}
