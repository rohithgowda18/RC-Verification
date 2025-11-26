package com.vehicle.SmartVehicle.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "RC number is required")
    private String rcNumber;

    private String qrCodeId;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Owner {
        private String name;
        private String phone;
        private String email;
        private String address;
        private String aadhaarLast4;
    }

    private Owner owner;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VehicleInfo {
        private String type;           // Car, Bike, Truck, etc.
        private String make;           // Maruti, Hyundai, etc.
        private String model;          // Swift, Creta, etc.
        private String variant;        // VXI, ZXI, etc.
        private String fuelType;       // Petrol, Diesel, CNG, etc.
        private String color;
        @NotNull(message = "Manufacture year is required")
        @Min(value = 1900, message = "Vehicle year must be valid")
        private Integer manufactureYear;
    }

    private VehicleInfo vehicleInfo;

    @Indexed(unique = true)
    @NotBlank(message = "Chassis number is required")
    private String chassisNumber;

    @Indexed(unique = true)
    @NotBlank(message = "Engine number is required")
    private String engineNumber;

    private String registrationState;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RegistrationInfo {
        private LocalDate registrationDate;
        private LocalDate validTill;
        private Boolean active;
    }

    private RegistrationInfo registrationInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Insurance {
        private String provider;
        private String policyNumber;
        private LocalDate validTill;
    }

    private Insurance insurance;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PUC {
        private String certificateNumber;
        private LocalDate validTill;
    }

    private PUC puc;

    private Boolean stolen;
    private Boolean suspicious;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Vehicle(String rcNumber, String ownerName, String chassisNumber, 
                   String engineNumber, String vehicleMake, String vehicleModel) {
        this.rcNumber = rcNumber;
        this.owner = Owner.builder().name(ownerName).build();
        this.chassisNumber = chassisNumber;
        this.engineNumber = engineNumber;
        this.vehicleInfo = VehicleInfo.builder().make(vehicleMake).model(vehicleModel).build();
        this.registrationInfo = RegistrationInfo.builder().active(true).build();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
