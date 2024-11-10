package com.aharon.auth.dto;

import com.aharon.clients.dto.ClientResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    ClientResponse client;
}
