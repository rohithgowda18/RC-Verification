package com.vehicle.SmartVehicle.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "fraud_flags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraudFlag {
    @Id
    private String id;

    @NotBlank(message = "Vehicle ID is required")
    private String vehicleId;

    @NotNull(message = "Fraud score is required")
    @DecimalMin(value = "0.0", message = "Fraud score must be between 0 and 1")
    @DecimalMax(value = "1.0", message = "Fraud score must be between 0 and 1")
    private double fraudScore; // 0-1

    private String flaggedBy; // User ID

    @NotBlank(message = "Flag type is required")
    private String flagType; // duplicate_chassis, duplicate_engine, expired_insurance, etc.

    @NotBlank(message = "Description is required")
    private String description;

    private boolean resolved;

    private LocalDateTime resolvedAt;

    private String resolutionNotes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt; // Soft delete timestamp

    public FraudFlag(String vehicleId, String flagType, String description) {
        this.vehicleId = vehicleId;
        this.flagType = flagType;
        this.description = description;
        this.resolved = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
