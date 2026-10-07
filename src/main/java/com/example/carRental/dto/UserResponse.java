package com.example.carRental.dto;

import com.example.carRental.model.User;

public record UserResponse(Long id, String fullName, String email) {
    public static UserResponse from(User u) {
        return new UserResponse(u.getId(), u.getFullName(), u.getEmail());
    }
}
