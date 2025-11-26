package com.vehicle.SmartVehicle.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "audit_logs")
@CompoundIndexes({
    @CompoundIndex(name = "entity_type_id_idx", def = "{'entityType': 1, 'entityId': 1, 'timestamp': -1}"),
    @CompoundIndex(name = "user_action_idx", def = "{'userId': 1, 'action': 1, 'timestamp': -1}"),
    @CompoundIndex(name = "timestamp_idx", def = "{'timestamp': -1}")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    @Id
    private String id;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Entity type is required")
    private String entityType; // User, Vehicle, FraudFlag, Verification

    @NotBlank(message = "Entity ID is required")
    private String entityId;

    @NotBlank(message = "Action is required")
    private String action; // CREATE, READ, UPDATE, DELETE, EXPORT

    @NotNull(message = "Old value is required")
    private String oldValue; // JSON string of previous value

    @NotNull(message = "New value is required")
    private String newValue; // JSON string of new value

    private String description;

    @Indexed
    private String ipAddress;

    private String userAgent;

    private String status; // SUCCESS, FAILURE

    private String errorMessage;

    @Indexed
    private LocalDateTime timestamp;

    public AuditLog(String userId, String entityType, String entityId, 
                    String action, String oldValue, String newValue, String description) {
        this.userId = userId;
        this.entityType = entityType;
        this.entityId = entityId;
        this.action = action;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.description = description;
        this.timestamp = LocalDateTime.now();
        this.status = "SUCCESS";
    }
}
