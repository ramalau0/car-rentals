package com.example.carRental.service;

import com.example.carRental.dto.VehicleRequest;
import com.example.carRental.dto.VehicleResponse;

public interface VehicleService {

    VehicleResponse create(VehicleRequest request);

    VehicleResponse update(Long id, VehicleRequest request);
}
