package com.example.carRental.service.impl;

import com.example.carRental.dto.RegisterRequest;
import com.example.carRental.exception.EmailAlreadyExistsException;
import com.example.carRental.model.Role;
import com.example.carRental.model.User;
import com.example.carRental.repository.UserRepository;
import com.example.carRental.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException(email);
        }
        User user = User.builder()
                .fullName(request.fullName().trim())
                .email(email)
                .phone(request.phone())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        return userRepository.save(user);
    }
}