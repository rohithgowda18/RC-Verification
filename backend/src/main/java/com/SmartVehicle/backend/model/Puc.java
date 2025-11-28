package com.SmartVehicle.backend.model;

import lombok.Data;

@Data
public class Puc {
    private String certificateNumber;
    private String validTill;
    private boolean stolen;
    private boolean suspicious;
}
