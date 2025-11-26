package com.vehicle.SmartVehicle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.SmartVehicle.dto.AuthResponse;
import com.vehicle.SmartVehicle.dto.SignInRequest;
import com.vehicle.SmartVehicle.dto.SignUpRequest;
import com.vehicle.SmartVehicle.model.User;
import com.vehicle.SmartVehicle.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        try {
            User user = authService.signUp(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponse(null, "Account created successfully", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse(null, e.getMessage(), null));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        try {
            User user = authService.signIn(request);
            String token = authService.generateToken(user);
            return ResponseEntity.ok(new AuthResponse(token, "Signed in successfully", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, e.getMessage(), null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(new AuthResponse(null, "Logged out successfully", null));
    }
}
