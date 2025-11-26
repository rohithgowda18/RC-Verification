package com.vehicle.SmartVehicle.dto;

import com.vehicle.SmartVehicle.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String message;
    private User user;
}
