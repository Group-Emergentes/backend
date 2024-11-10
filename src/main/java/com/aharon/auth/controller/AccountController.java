package com.aharon.auth.controller;

import com.aharon.auth.dto.AuthResponse;
import com.aharon.auth.dto.LoginRequest;
import com.aharon.auth.service.AccountService;
import com.aharon.clients.dto.ClientRequest;
import com.aharon.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody ClientRequest request) {
        AuthResponse authResponse = accountService.register(request);

        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Client created successfully.");
        apiResponse.setData(authResponse);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {

        AuthResponse authResponse = accountService.login(request.getEmail(), request.getPassword());

        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Login successfully.");
        apiResponse.setData(authResponse);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

}
