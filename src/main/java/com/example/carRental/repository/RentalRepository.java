package com.example.carRental.repository;

import com.example.carRental.model.Rental;
import com.example.carRental.model.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUserId(Long userId);

    //checks f the vehicle is inbound and not rented out
    boolean existsByVehicleId(Long vehicleId);
    boolean existsByVehicleIdAndStatus(Long vehicleId, RentalStatus status);
}
