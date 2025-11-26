package com.vehicle.SmartVehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraudCheckDTO {
    private String type;
    private String message;
    private String severity; // low, medium, high
}
