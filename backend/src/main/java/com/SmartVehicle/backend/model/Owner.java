package com.SmartVehicle.backend.model;

import lombok.Data;

@Data
public class Owner {
    private String name;
    private String phone;
    private String email;
    private String address;
    private String aadhaarLast4;
}
