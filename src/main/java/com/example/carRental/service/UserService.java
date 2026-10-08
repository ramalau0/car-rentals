package com.example.carRental.service;

import com.example.carRental.dto.LoginRequest;
import com.example.carRental.dto.RegisterRequest;
import com.example.carRental.model.User;

public interface UserService {
    User register(RegisterRequest request);
    User login(LoginRequest request);
}