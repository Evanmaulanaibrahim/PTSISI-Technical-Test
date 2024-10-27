package com.evanmaulanaibrahim.backenddev.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID vendorId;

    @Column(name = "vendorName", nullable = false, unique = true, length = 100)
    private String vendorName;

    @Column(name = "vendorAddress", nullable = false, length = 100)
    private String vendorAddress;

    @Column(name = "vendorEmail", nullable = false, length = 100)
    private String vendorEmail;

    @Column(name = "vendorPhone", nullable = false, length = 100)
    private String vendorPhone;
}
