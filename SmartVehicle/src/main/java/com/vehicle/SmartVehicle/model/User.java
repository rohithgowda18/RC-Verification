package com.vehicle.SmartVehicle.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Role is required")
    private String role; // public, buyer, police, rto_admin

    private boolean isActive = true;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt; // Soft delete timestamp

    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = "public";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isActive = true;
    }
}
