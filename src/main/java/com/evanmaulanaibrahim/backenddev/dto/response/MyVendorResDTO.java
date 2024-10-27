package com.evanmaulanaibrahim.backenddev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyVendorResDTO {
    private UUID vendorId;
    private String vendorName;
    private String vendorEmail;
    private String vendorAddress;
    private String vendorPhone;
}
