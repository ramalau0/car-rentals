package com.example.carRental.service.impl;

import com.example.carRental.dto.VehicleRequest;
import com.example.carRental.dto.VehicleResponse;
import com.example.carRental.exception.ConflictException;
import com.example.carRental.exception.NotFoundException;
import com.example.carRental.model.RentalStatus;
import com.example.carRental.model.Vehicle;
import com.example.carRental.model.VehicleStatus;
import com.example.carRental.repository.RentalRepository;
import com.example.carRental.repository.VehicleRepository;
import com.example.carRental.service.VehicleDeleteResult;
import com.example.carRental.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final RentalRepository rentalRepository;

    @Override
    public VehicleResponse create(VehicleRequest request) {
        if (vehicleRepository.existsByRegistrationNumber(request.registrationNumber())) {
            throw new ConflictException("Registration number already exists");
        }
        Vehicle vehicle = Vehicle.builder()
                .make(request.make())
                .model(request.model())
                .year(request.year())
                .registrationNumber(request.registrationNumber())
                .dailyRate(request.dailyRate())
                .status(VehicleStatus.AVAILABLE) // always starts available
                .build();
        return VehicleResponse.from(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleResponse update(Long id, VehicleRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));

        if (vehicleRepository.existsByRegistrationNumberAndIdNot(request.registrationNumber(), id)) {
            throw new ConflictException("Registration number already exists");
        }
        // status is deliberately not editable here; renting and returning control it
        vehicle.setMake(request.make());
        vehicle.setModel(request.model());
        vehicle.setYear(request.year());
        vehicle.setRegistrationNumber(request.registrationNumber());
        vehicle.setDailyRate(request.dailyRate());
        return VehicleResponse.from(vehicleRepository.save(vehicle));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponse> findAll(VehicleStatus status) {
        List<Vehicle> vehicles = (status == null)
                ? vehicleRepository.findAll()
                : vehicleRepository.findByStatus(status);

        return vehicles.stream()
                .map(VehicleResponse::from)
                .toList();
    }
    @Override
    @Transactional
    public VehicleDeleteResult delete(Long id) {
        Optional<Vehicle> found = vehicleRepository.findByIdForUpdate(id);
        if (found.isEmpty()) {
            return VehicleDeleteResult.NOT_FOUND;
        }
        Vehicle vehicle = found.get();

        if (rentalRepository.existsByVehicleIdAndStatus(id, RentalStatus.ACTIVE)) {
            return VehicleDeleteResult.HAS_ACTIVE_RENTAL;
        }
        if (rentalRepository.existsByVehicleId(id)) {
            vehicle.setStatus(VehicleStatus.RETIRED); // has history: keep the row
            return VehicleDeleteResult.RETIRED;
        }
        vehicleRepository.delete(vehicle);
        return VehicleDeleteResult.DELETED;
    }
}
