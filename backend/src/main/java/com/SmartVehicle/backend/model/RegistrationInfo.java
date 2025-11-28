package com.SmartVehicle.backend.model;

import lombok.Data;

@Data
public class RegistrationInfo {
    private String registrationDate;
    private String validTill;
    private boolean active;
}
