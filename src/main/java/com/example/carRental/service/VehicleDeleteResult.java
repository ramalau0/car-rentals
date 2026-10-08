package com.example.carRental.service;

public enum VehicleDeleteResult {
    DELETED,            // never rented: row removed
    RETIRED,            // has rental history: status set to RETIRED, row kept
    NOT_FOUND,
    HAS_ACTIVE_RENTAL
}