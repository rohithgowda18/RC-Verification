package com.SmartVehicle.backend.model;

import lombok.Data;

@Data
public class Insurance {
    private String provider;
    private String policyNumber;
    private String validTill;
}
