package com.evanmaulanaibrahim.backenddev.service.specification;

import com.evanmaulanaibrahim.backenddev.dto.request.MyVendorRequestDTO;
import com.evanmaulanaibrahim.backenddev.model.Vendor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VendorSpesification {
    public static Specification<Vendor> vendorFilter(MyVendorRequestDTO myVendorRequestDTO, UUID userId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();

            predicates.add(criteriaBuilder.equal(root.get("user").get("userId"), userId));

            if (myVendorRequestDTO.getVendorName() != null) {
                String vendorNameValue = "%" + myVendorRequestDTO.getVendorName().toLowerCase() + "%";
                Predicate vendorNamePredicates = criteriaBuilder.like(criteriaBuilder.lower(root.get("vendorName")),
                        vendorNameValue);
                predicates.add(vendorNamePredicates);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}

