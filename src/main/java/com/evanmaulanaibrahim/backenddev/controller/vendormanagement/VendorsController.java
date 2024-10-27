package com.evanmaulanaibrahim.backenddev.controller.vendormanagement;

import com.evanmaulanaibrahim.backenddev.dto.request.CreateVendorRequest;
import com.evanmaulanaibrahim.backenddev.dto.response.MessageResponse;
import com.evanmaulanaibrahim.backenddev.service.VendorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendor-management")
public class VendorsController {
    @Autowired
    private VendorsService vendorsService;

    @PostMapping(path = "/vendors/add-vendor", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse addVendor(@RequestBody CreateVendorRequest createVendorRequest) {
        return vendorsService.create(createVendorRequest);
    }
}
