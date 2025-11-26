package com.vehicle.SmartVehicle.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicle.SmartVehicle.dto.FraudCheckDTO;
import com.vehicle.SmartVehicle.dto.FraudCheckResponse;
import com.vehicle.SmartVehicle.model.Vehicle;
import com.vehicle.SmartVehicle.model.Verification;
import com.vehicle.SmartVehicle.repository.VehicleRepository;
import com.vehicle.SmartVehicle.repository.VerificationRepository;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    public Vehicle searchVehicle(String rcNumber) {
        return vehicleRepository.findByRcNumber(rcNumber.toUpperCase())
                .orElseThrow(() -> new RuntimeException("RC number not found in database"));
    }

    public FraudCheckResponse performFraudChecks(String vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        List<FraudCheckDTO> checks = new ArrayList<>();
        double fraudScore = 0;

        // Check for duplicate chassis
        long chassisCount = vehicleRepository.countByChassisNumber(vehicle.getChassisNumber());
        if (chassisCount > 1) {
            FraudCheckDTO dto = new FraudCheckDTO();
            dto.setType("Duplicate Chassis");
            dto.setMessage("Chassis number found in " + chassisCount + " vehicles");
            dto.setSeverity("high");
            checks.add(dto);
            fraudScore += 0.4;
        }

        // Check for duplicate engine
        long engineCount = vehicleRepository.countByEngineNumber(vehicle.getEngineNumber());
        if (engineCount > 1) {
            FraudCheckDTO dto = new FraudCheckDTO();
            dto.setType("Duplicate Engine");
            dto.setMessage("Engine number found in " + engineCount + " vehicles");
            dto.setSeverity("high");
            checks.add(dto);
            fraudScore += 0.4;
        }

        // Check insurance expiry
        if (vehicle.getInsurance() != null && vehicle.getInsurance().getValidTill() != null) {
            if (vehicle.getInsurance().getValidTill().isBefore(LocalDate.now())) {
                FraudCheckDTO dto = new FraudCheckDTO();
                dto.setType("Expired Insurance");
                dto.setMessage("Vehicle insurance has expired");
                dto.setSeverity("medium");
                checks.add(dto);
                fraudScore += 0.2;
            }
        }

        // Check PUC expiry
        if (vehicle.getPuc() != null && vehicle.getPuc().getValidTill() != null) {
            if (vehicle.getPuc().getValidTill().isBefore(LocalDate.now())) {
                FraudCheckDTO dto = new FraudCheckDTO();
                dto.setType("Expired PUC");
                dto.setMessage("Pollution certificate has expired");
                dto.setSeverity("low");
                checks.add(dto);
                fraudScore += 0.1;
            }
        }

        // Check if vehicle is marked as stolen
        if (vehicle.getSuspicious() != null && vehicle.getSuspicious()) {
            FraudCheckDTO dto = new FraudCheckDTO();
            dto.setType("Suspicious Vehicle");
            dto.setMessage("Vehicle marked as suspicious");
            dto.setSeverity("high");
            checks.add(dto);
            fraudScore += 0.4;
        }

        // Check if vehicle is marked as stolen
        if (vehicle.getStolen() != null && vehicle.getStolen()) {
            FraudCheckDTO dto = new FraudCheckDTO();
            dto.setType("Stolen Vehicle");
            dto.setMessage("Vehicle reported as stolen");
            dto.setSeverity("critical");
            checks.add(dto);
            fraudScore += 1.0;
        }

        // Check registration validity
        if (vehicle.getRegistrationInfo() != null && vehicle.getRegistrationInfo().getValidTill() != null) {
            if (vehicle.getRegistrationInfo().getValidTill().isBefore(LocalDate.now())) {
                FraudCheckDTO dto = new FraudCheckDTO();
                dto.setType("Expired Registration");
                dto.setMessage("Vehicle registration has expired");
                dto.setSeverity("high");
                checks.add(dto);
                fraudScore += 0.3;
            }
        }

        fraudScore = Math.min(fraudScore, 1.0);

        String result;
        if (fraudScore > 0.5) {
            result = "suspicious";
        } else if (fraudScore > 0) {
            result = "concerns";
        } else {
            result = "verified";
        }

        FraudCheckResponse response = new FraudCheckResponse();
        response.setFraudChecks(checks);
        response.setFraudScore(fraudScore);
        response.setResult(result);
        return response;
    }

    public Verification logVerification(String vehicleId, String userId, String verificationType, String result, double fraudScore) {
        Verification verification = new Verification(vehicleId, userId, verificationType, result);
        verification.setFraudScore(fraudScore);
        return verificationRepository.save(verification);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
