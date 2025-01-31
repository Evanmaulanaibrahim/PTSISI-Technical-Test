package com.evanmaulanaibrahim.backenddev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    private int statusCode;
    private String message;
    private String details;
}
