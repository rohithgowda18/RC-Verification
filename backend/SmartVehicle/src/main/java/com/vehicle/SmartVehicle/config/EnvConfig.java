package com.vehicle.SmartVehicle.config;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Configuration to load environment variables from .env file
 */
@Configuration
public class EnvConfig {
    static {
        String envPath = ".env";
        try {
            if (Files.exists(Paths.get(envPath))) {
                Dotenv dotenv = Dotenv.configure()
                        .directory(".")
                        .load();
                // Set environment variables as system properties
                dotenv.entries().forEach(entry -> {
                    System.setProperty(entry.getKey(), entry.getValue());
                });
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not load .env file: " + e.getMessage());
        }
    }
}
