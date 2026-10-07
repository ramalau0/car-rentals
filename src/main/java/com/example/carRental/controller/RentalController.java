package com.example.carRental.controller;

import com.example.carRental.dto.RentalRequest;
import com.example.carRental.dto.RentalResponse;
import com.example.carRental.security.CustomUserDetails;
import com.example.carRental.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public ResponseEntity<RentalResponse> rent(@AuthenticationPrincipal CustomUserDetails caller,
                                               @Valid @RequestBody RentalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.rent(caller.getId(), request));
    }

    @GetMapping("/{userId}")
    public List<RentalResponse> getByUser(@AuthenticationPrincipal CustomUserDetails caller,
                                          @PathVariable Long userId) {
        return rentalService.getRentalsByUser(userId, caller.getId(), caller.getRole());
    }

    @PostMapping("/{id}/return")
    public RentalResponse returnRental(@AuthenticationPrincipal CustomUserDetails caller,
                                       @PathVariable Long id) {
        return rentalService.returnRental(id, caller.getId(), caller.getRole());
    }
}
