package com.vehicle.SmartVehicle.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.vehicle.SmartVehicle.model.Verification;

@Repository
public interface VerificationRepository extends MongoRepository<Verification, String> {
    List<Verification> findByVehicleId(String vehicleId);
    
    List<Verification> findByVerifiedBy(String userId);
    
    List<Verification> findByResult(String result);
    
    List<Verification> findByVerificationType(String type);
    
    @Query("{ 'vehicleId': ?0, 'result': ?1 }")
    List<Verification> findByVehicleIdAndResult(String vehicleId, String result);
    
    @Query("{ 'createdAt': { $gte: ?0, $lte: ?1 } }")
    List<Verification> findVerificationsBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("{ 'fraudScore': { $gte: ?0 } }")
    List<Verification> findHighRiskVerifications(double minScore);
    
    @Query("{ 'result': ?0, 'createdAt': { $gte: ?1 } }")
    Page<Verification> findByResultSince(String result, LocalDateTime since, Pageable pageable);
    
    @Query("{ 'fraudScore': { $gte: ?0, $lte: ?1 } }")
    Page<Verification> findByFraudScoreBetween(double minScore, double maxScore, Pageable pageable);
    
    long countByResult(String result);
    
    long countByVerificationType(String type);
}
