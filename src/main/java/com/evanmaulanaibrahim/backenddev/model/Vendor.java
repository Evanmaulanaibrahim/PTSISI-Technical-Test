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
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID vendorId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "vendor_name", nullable = false, unique = true, length = 100)
    private String vendorName;

    @Column(name = "vendor_address", nullable = false, length = 100)
    private String vendorAddress;

    @Column(name = "vendor_email", nullable = false, unique = true, length = 100)
    private String vendorEmail;

    @Column(name = "vendor_phone", nullable = false, length = 15)
    private String vendorPhone;
}
