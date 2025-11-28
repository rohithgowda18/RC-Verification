package com.SmartVehicle.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data   // IMPORTANT (generates setters & getters automatically)
@Document(collection = "vehicles")
public class Rc {

    @Id
    private String id;

    private String rcNumber;
    private String qrCodeId;
    private int ownersCount;
    private List<String> previousOwners;

    private Owner owner;
    private VehicleInfo vehicleInfo;
    private RegistrationInfo registrationInfo;
    private Insurance insurance;
    private Puc puc;
    private Instant createdAt;
    private Instant updatedAt;

    // Explicit getters/setters to ensure availability without Lombok at compile-time
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

}
