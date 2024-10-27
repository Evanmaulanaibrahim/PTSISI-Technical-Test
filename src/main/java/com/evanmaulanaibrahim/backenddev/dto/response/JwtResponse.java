package com.evanmaulanaibrahim.backenddev.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private UUID id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String role;

    public JwtResponse(String token, UUID id, String username, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
