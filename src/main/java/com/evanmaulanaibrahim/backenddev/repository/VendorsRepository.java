package com.evanmaulanaibrahim.backenddev.repository;

import com.evanmaulanaibrahim.backenddev.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface VendorsRepository extends JpaRepository<Vendor, UUID> {

    @Query("SELECT v FROM Vendor v WHERE v.vendorId = :vendorId AND v.user.userId = :userId")
    Optional<Vendor> findByVendorIdAndUserId(@Param("vendorId") UUID vendorId, @Param("userId") UUID userId);
}
