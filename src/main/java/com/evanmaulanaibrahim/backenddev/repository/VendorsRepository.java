package com.evanmaulanaibrahim.backenddev.repository;

import com.evanmaulanaibrahim.backenddev.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VendorsRepository extends JpaRepository<Vendor, UUID> {

}
