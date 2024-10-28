package com.evanmaulanaibrahim.backenddev.service;

import com.evanmaulanaibrahim.backenddev.dto.request.CreateVendorRequest;
import com.evanmaulanaibrahim.backenddev.dto.request.MyVendorRequestDTO;
import com.evanmaulanaibrahim.backenddev.dto.request.UpdateVendorRequest;
import com.evanmaulanaibrahim.backenddev.dto.response.MessageResponse;
import com.evanmaulanaibrahim.backenddev.dto.response.MyVendorResDTO;
import com.evanmaulanaibrahim.backenddev.dto.response.ResponseBodyDTO;
import com.evanmaulanaibrahim.backenddev.dto.response.VendorDTO;
import com.evanmaulanaibrahim.backenddev.exception.classes.DataNotFoundException;
import com.evanmaulanaibrahim.backenddev.exception.classes.UnauthorizedUserException;
import com.evanmaulanaibrahim.backenddev.model.User;
import com.evanmaulanaibrahim.backenddev.model.Vendor;
import com.evanmaulanaibrahim.backenddev.repository.UsersRepository;
import com.evanmaulanaibrahim.backenddev.repository.VendorsRepository;
import com.evanmaulanaibrahim.backenddev.security.service.UserDetailsImplement;
import com.evanmaulanaibrahim.backenddev.service.specification.VendorSpesification;
import jakarta.persistence.EntityNotFoundException;
import lib.i18n.utility.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VendorsService {
    @Autowired
    private VendorsRepository vendorsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MessageUtil messageUtil;

    @Transactional
    public MessageResponse create(CreateVendorRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetailsImplement)) {
            throw new UnauthorizedUserException(messageUtil.get("application.error.unauthorized-user.detail"));
        }

        UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
        UUID userId = userDetails.getId();

        // Retrieve the User entity associated with the authenticated user
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.get("application.error.user.not-found")));

        Vendor newVendor = Vendor.builder()
                .vendorName(request.getVendorName())
                .vendorEmail(request.getVendorEmail())
                .vendorAddress(request.getVendorAddress())
                .vendorPhone(request.getVendorPhone())
                .user(user)
                .build();

        vendorsRepository.save(newVendor);

        String responseMessage = messageUtil.get("application.success.add.vendor", request.getVendorName());
        int statusCode = HttpStatus.OK.value();
        String status = HttpStatus.OK.getReasonPhrase();

        log.info(responseMessage, statusCode, status);

        return new MessageResponse(responseMessage, statusCode, status);
    }

    @Transactional
    public MessageResponse updateById(UpdateVendorRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetailsImplement)) {
            throw new UnauthorizedUserException(messageUtil.get("application.error.unauthorized-user.detail"));
        }
        UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
        UUID userId = userDetails.getId();

        Vendor existingVendor = vendorsRepository.findByVendorIdAndUserId(request.getVendorId(), userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageUtil.get("application.error.data-not-found", request.getVendorId())));

        existingVendor.setVendorName(request.getVendorName());
        existingVendor.setVendorEmail(request.getVendorEmail());
        existingVendor.setVendorAddress(request.getVendorAddress());
        existingVendor.setVendorPhone(request.getVendorPhone());

        vendorsRepository.save(existingVendor);

        String responseMessage = messageUtil.get("application.success.update.vendor", request.getVendorName());
        int statusCode = HttpStatus.OK.value();
        String status = HttpStatus.OK.getReasonPhrase();

        log.info(responseMessage, statusCode, status);

        return new MessageResponse(responseMessage, statusCode, status);
    }

    public ResponseBodyDTO getVendorById(UUID vendorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetailsImplement)) {
            throw new UnauthorizedUserException(messageUtil.get("application.error.unauthorized-user.detail"));
        }

        UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
        UUID userId = userDetails.getId();

        Vendor vendor = vendorsRepository.findByVendorIdAndUserId(vendorId, userId)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.get("application.error.data-not-found", vendorId)));

        // Map Vendor to VendorDTO
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setVendorId(vendor.getVendorId());
        vendorDTO.setVendorName(vendor.getVendorName());
        vendorDTO.setVendorAddress(vendor.getVendorAddress());
        vendorDTO.setVendorEmail(vendor.getVendorEmail());
        vendorDTO.setVendorPhone(vendor.getVendorPhone());
        vendorDTO.setUserId(vendor.getUser() != null ? vendor.getUser().getUserId() : null);

        return ResponseBodyDTO.builder()
                .total(1)
                .data(vendorDTO)
                .message(messageUtil.get("application.success.vendor.found"))
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    public ResponseEntity<ResponseBodyDTO> getMyVendor(MyVendorRequestDTO myVendorRequestDTO, Pageable page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetailsImplement)) {
            throw new UnauthorizedUserException(messageUtil.get("application.error.unauthorized-user.detail"));
        }

        UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
        UUID userId = userDetails.getId();

        Specification<Vendor> vendorSpec = VendorSpesification.vendorFilter(myVendorRequestDTO, userId);

        Page<Vendor> vendors = vendorsRepository.findAll(vendorSpec, page);

        if (vendors.isEmpty()) {
            throw new DataNotFoundException(messageUtil.get("application.error.data-not-found"));
        }

        long totalData = vendors.getTotalElements();
        List<MyVendorResDTO> response = vendors.getContent().stream()
                .map(vendor -> new MyVendorResDTO(
                        vendor.getVendorId(),
                        vendor.getVendorName(),
                        vendor.getVendorEmail(),
                        vendor.getVendorAddress(),
                        vendor.getVendorPhone()
                ))
                .collect(Collectors.toList());

        ResponseBodyDTO responseBody = ResponseBodyDTO.builder()
                .total(totalData)
                .data(response)
                .message(messageUtil.get("application.success.load", "My Vendor"))
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .build();

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    public ResponseBodyDTO deleteVendorById(UUID vendorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetailsImplement)) {
            throw new UnauthorizedUserException(messageUtil.get("application.error.unauthorized-user.detail"));
        }

        UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
        UUID userId = userDetails.getId();

        Vendor vendor = vendorsRepository.findByVendorIdAndUserId(vendorId, userId)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.get("application.error.data-not-found", vendorId)));

        vendorsRepository.delete(vendor);
        
        return ResponseBodyDTO.builder()
                .total(0)
                .data(null)
                .message(messageUtil.get("application.success.vendor.deleted"))
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.getReasonPhrase())
                .build();
    }


}
