package com.vehicle.SmartVehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraudCheckResponse {
    private java.util.List<FraudCheckDTO> fraudChecks;
    private double fraudScore;
    private String result; // verified, concerns, suspicious
}
