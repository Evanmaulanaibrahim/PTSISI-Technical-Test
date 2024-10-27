package com.evanmaulanaibrahim.backenddev.service;

import com.evanmaulanaibrahim.backenddev.dto.request.CreateVendorRequest;
import com.evanmaulanaibrahim.backenddev.dto.request.UpdateVendorRequest;
import com.evanmaulanaibrahim.backenddev.dto.response.MessageResponse;
import com.evanmaulanaibrahim.backenddev.exception.classes.UnauthorizedUserException;
import com.evanmaulanaibrahim.backenddev.model.Vendor;
import com.evanmaulanaibrahim.backenddev.repository.VendorsRepository;
import com.evanmaulanaibrahim.backenddev.security.service.UserDetailsImplement;
import jakarta.persistence.EntityNotFoundException;
import lib.i18n.utility.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class VendorsService {
    @Autowired
    private VendorsRepository vendorsRepository;

    @Autowired
    private MessageUtil messageUtil;

    @Transactional
    public MessageResponse create(CreateVendorRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetailsImplement)) {
            throw new UnauthorizedUserException(messageUtil.get("application.error.unauthorized-user.detail"));
        }
        UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
        UUID userId = userDetails.getId();


        Vendor newVendor = Vendor.builder()
                .vendorName(request.getVendorName())
                .vendorEmail(request.getVendorEmail())
                .vendorAddress(request.getVendorAddress())
                .vendorPhone(request.getVendorPhone())
                .build();

        vendorsRepository.save(newVendor);

        String responseMessage = messageUtil.get("application.success.add.vendor", request.getVendorName());
        int statusCode = HttpStatus.OK.value();
        String status = HttpStatus.OK.getReasonPhrase();

        log.info(responseMessage, statusCode, status);

        return new MessageResponse(responseMessage, statusCode, status);
    }

    @Transactional
    public MessageResponse updateById(UpdateVendorRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetailsImplement)) {
            throw new UnauthorizedUserException(messageUtil.get("application.error.unauthorized-user.detail"));
        }
        UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
        UUID userId = userDetails.getId();

        Vendor existingVendor = vendorsRepository.findById(request.getVendorId())
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

}
