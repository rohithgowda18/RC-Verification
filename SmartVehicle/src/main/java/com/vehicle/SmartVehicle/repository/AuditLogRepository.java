package com.vehicle.SmartVehicle.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.vehicle.SmartVehicle.model.AuditLog;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
    
    List<AuditLog> findByUserId(String userId);
    
    List<AuditLog> findByEntityType(String entityType);
    
    List<AuditLog> findByEntityId(String entityId);
    
    List<AuditLog> findByAction(String action);
    
    List<AuditLog> findByStatus(String status);
    
    @Query("{ 'timestamp': { $gte: ?0, $lte: ?1 } }")
    List<AuditLog> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("{ 'userId': ?0, 'timestamp': { $gte: ?1 } }")
    List<AuditLog> findUserActivitiesSince(String userId, LocalDateTime since);
    
    @Query("{ 'entityType': ?0, 'action': ?1, 'timestamp': { $gte: ?2 } }")
    List<AuditLog> findByEntityTypeAndActionSince(String entityType, String action, LocalDateTime since);
    
    Page<AuditLog> findByUserIdOrderByTimestampDesc(String userId, Pageable pageable);
    
    Page<AuditLog> findByEntityIdOrderByTimestampDesc(String entityId, Pageable pageable);
    
    long countByStatus(String status);
    
    long countByAction(String action);
}
