package com.vehicle.SmartVehicle.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "verifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Verification {
    @Id
    private String id;

    @NotBlank(message = "Vehicle ID is required")
    private String vehicleId;

    @NotBlank(message = "Verified by user ID is required")
    private String verifiedBy; // User ID

    @NotBlank(message = "Verification type is required")
    private String verificationType; // qr_scan, manual_search, batch_check

    @NotBlank(message = "Result is required")
    private String result; // verified, suspicious, blocked

    @NotNull(message = "Fraud score is required")
    private double fraudScore;

    private String verificationIP;

    private String verificationLocation;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt; // Soft delete timestamp

    public Verification(String vehicleId, String verifiedBy, String verificationType, String result) {
        this.vehicleId = vehicleId;
        this.verifiedBy = verifiedBy;
        this.verificationType = verificationType;
        this.result = result;
        this.fraudScore = 0.0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void setFraudScore(double fraudScore) {
        this.fraudScore = fraudScore;
    }
}
