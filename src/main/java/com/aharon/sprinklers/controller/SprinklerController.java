package com.aharon.sprinklers.controller;

import com.aharon.common.dto.ApiResponse;
import com.aharon.sprinklers.dto.CreateSprinkler;
import com.aharon.sprinklers.dto.SprinklerResponse;
import com.aharon.sprinklers.service.SprinklerService;
import com.aharon.zones.dto.CreateZone;
import com.aharon.zones.dto.ZoneResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sprinklers")
@AllArgsConstructor
public class SprinklerController {
    private final SprinklerService sprinklerService;
    @PostMapping
    public ResponseEntity<ApiResponse<SprinklerService>> addSprinkler(@Valid @RequestBody CreateSprinkler createSprinkler) {
        SprinklerResponse sprinklerResponse = sprinklerService.addSprinkler(createSprinkler);

        ApiResponse<SprinklerResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Sprinkler created successfully.");
        apiResponse.setData(sprinklerResponse);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
