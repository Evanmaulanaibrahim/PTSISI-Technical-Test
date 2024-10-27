package com.evanmaulanaibrahim.backenddev.controller.vendormanagement;

import com.evanmaulanaibrahim.backenddev.dto.request.CreateVendorRequest;
import com.evanmaulanaibrahim.backenddev.dto.request.MyVendorRequestDTO;
import com.evanmaulanaibrahim.backenddev.dto.request.UpdateVendorRequest;
import com.evanmaulanaibrahim.backenddev.dto.response.MessageResponse;
import com.evanmaulanaibrahim.backenddev.dto.response.ResponseBodyDTO;
import com.evanmaulanaibrahim.backenddev.service.VendorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/vendor-management")
public class VendorsController {
    @Autowired
    private VendorsService vendorsService;

    @PostMapping(path = "/vendors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse addVendor(@RequestBody CreateVendorRequest createVendorRequest) {
        return vendorsService.create(createVendorRequest);
    }

    @PutMapping(path = "/vendors/{vendorId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse updateVendor(@RequestBody UpdateVendorRequest updateVendorRequest, @PathVariable UUID vendorId) {
        updateVendorRequest.setVendorId(vendorId);
        return vendorsService.updateById(updateVendorRequest);
    }

    @GetMapping("/vendors/{vendorId}")
    public ResponseEntity<ResponseBodyDTO> getVendorById(@PathVariable UUID vendorId) {
        ResponseBodyDTO response = vendorsService.getVendorById(vendorId);
        return new ResponseEntity<>(response, HttpStatus.valueOf((int) response.getStatusCode()));
    }

    @GetMapping("/vendors")
    public ResponseEntity<ResponseBodyDTO> getAllVendors(
            @PageableDefault(page = 0, size = 8, sort = "vendorName", direction = Sort.Direction.ASC) Pageable page,
            @ModelAttribute MyVendorRequestDTO myVendorRequestDTO) {

        return vendorsService.getMyVendor(myVendorRequestDTO, page);
    }


}
