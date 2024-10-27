package com.evanmaulanaibrahim.backenddev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {
    private UUID vendorId;
    private String vendorName;
    private String vendorAddress;
    private String vendorEmail;
    private String vendorPhone;
    private UUID userId; // Include userId if needed

    // Constructor, getters, and setters
}

