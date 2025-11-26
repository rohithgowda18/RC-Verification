package com.vehicle.SmartVehicle.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.vehicle.SmartVehicle.model.FraudFlag;

@Repository
public interface FraudFlagRepository extends MongoRepository<FraudFlag, String> {
    List<FraudFlag> findByVehicleId(String vehicleId);
    
    List<FraudFlag> findByVehicleIdAndResolved(String vehicleId, boolean resolved);
    
    List<FraudFlag> findByFlagType(String flagType);
    
    List<FraudFlag> findByResolved(boolean resolved);
    
    @Query("{ 'vehicleId': ?0, 'fraudScore': { $gte: ?1 } }")
    List<FraudFlag> findHighRiskFlags(String vehicleId, double minScore);
    
    @Query("{ 'createdAt': { $gte: ?0 } }")
    List<FraudFlag> findFlagsSince(LocalDateTime since);
    
    @Query("{ 'vehicleId': ?0, 'resolved': false }")
    List<FraudFlag> findUnresolvedFlagsForVehicle(String vehicleId);
    
    @Query("{ 'fraudScore': { $gte: ?0 }, 'resolved': false }")
    Page<FraudFlag> findHighRiskUnresolved(double minScore, Pageable pageable);
    
    @Query("{ 'createdAt': { $gte: ?0, $lte: ?1 } }")
    Page<FraudFlag> findFlagsBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    long countByFlagType(String flagType);
    
    long countByResolved(boolean resolved);
}
