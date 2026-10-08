package com.example.carRental.service;

import com.example.carRental.dto.VehicleRequest;
import com.example.carRental.dto.VehicleResponse;
import com.example.carRental.model.VehicleStatus;

import java.util.List;

public interface VehicleService {

    VehicleResponse create(VehicleRequest request);
    VehicleResponse update(Long id, VehicleRequest request);

    VehicleDeleteResult delete(Long id);

    List<VehicleResponse> findAll(VehicleStatus status);
}
