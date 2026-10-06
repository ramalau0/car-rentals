package com.example.carRental.exception;

public record ErrorResponse(int status, String error, String message) {
}
