package com.vehicle.SmartVehicle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vehicle.SmartVehicle.dto.SignInRequest;
import com.vehicle.SmartVehicle.dto.SignUpRequest;
import com.vehicle.SmartVehicle.model.User;
import com.vehicle.SmartVehicle.repository.UserRepository;
import com.vehicle.SmartVehicle.util.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        if (request.getPassword().length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }

        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFullName() != null ? request.getFullName() : "User"
        );

        user = userRepository.save(user);
        return user;
    }

    public User signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    public String generateToken(User user) {
        return jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
    }
}
