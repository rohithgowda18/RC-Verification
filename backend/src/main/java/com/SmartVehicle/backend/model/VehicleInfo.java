package com.SmartVehicle.backend.model;

import lombok.Data;

@Data
public class VehicleInfo {
    private String type;
    private String make;
    private String model;
    private String variant;
    private String fuelType;
    private String color;
    private int manufactureYear;
    private String chassisNumber;
    private String engineNumber;
    private String registrationState;
}
