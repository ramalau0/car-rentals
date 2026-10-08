package com.example.carRental.controller;

import com.example.carRental.dto.VehicleRequest;
import com.example.carRental.dto.VehicleResponse;
import com.example.carRental.model.VehicleStatus;
import com.example.carRental.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VehicleResponse> create(@Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public VehicleResponse update(@PathVariable Long id, @Valid @RequestBody VehicleRequest request) {
        return vehicleService.update(id, request);
    }

    @GetMapping
    public List<VehicleResponse> getAll(@RequestParam(required = false) VehicleStatus status) {
        return vehicleService.findAll(status);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return switch (vehicleService.delete(id)) {
            case DELETED, RETIRED -> ResponseEntity.noContent().build();
            case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Vehicle not found: " + id));
            case HAS_ACTIVE_RENTAL -> ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Vehicle " + id + " has an active rental and cannot be removed"));
        };
    }
}
