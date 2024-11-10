package com.aharon.sprinklers.controller;

import com.aharon.common.dto.ApiResponse;
import com.aharon.sprinklers.dto.ActiveRequest;
import com.aharon.sprinklers.dto.CreateSprinkler;
import com.aharon.sprinklers.dto.SprinklerResponse;
import com.aharon.sprinklers.service.SprinklerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprinklers")
@AllArgsConstructor
public class SprinklerController {
    private final SprinklerService sprinklerService;

    @PostMapping
    public ResponseEntity<ApiResponse<SprinklerResponse>> addSprinkler(@Valid @RequestBody CreateSprinkler createSprinkler) {
        SprinklerResponse sprinklerResponse = sprinklerService.addSprinkler(createSprinkler);

        ApiResponse<SprinklerResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Sprinkler created successfully.");
        apiResponse.setData(sprinklerResponse);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }

    @GetMapping("zone/{zoneId}")
    public ResponseEntity<ApiResponse<List<SprinklerResponse>>> getSprinklersByZoneId(@PathVariable Long zoneId){
        List<SprinklerResponse> sprinklerResponseList = sprinklerService.getAllSprinklersByZoneId(zoneId);

        ApiResponse<List<SprinklerResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("All Sprinklers by zone "+ zoneId);
        apiResponse.setData(sprinklerResponseList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("{sprinklerId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteSprinkler(@PathVariable String sprinklerId){
        Boolean deleted = sprinklerService.deleteSprinkler(sprinklerId);

        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Sprinklers deleted "+ sprinklerId);
        apiResponse.setData(deleted);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("zone/active-sprinklers")
    public ResponseEntity<ApiResponse<Boolean>> activeSprinklersByZone(@RequestBody ActiveRequest activeRequest){
        Boolean active = sprinklerService.activeAllSprinklersByZoneId(activeRequest);

        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Sprinklers activated in zone "+ activeRequest.getZoneId());
        apiResponse.setData(active);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @PostMapping("zone/disable-sprinklers/{zoneId}")
    public ResponseEntity<ApiResponse<Boolean>> disableSprinklersByZone(@PathVariable Long zoneId){
        Boolean disable = sprinklerService.disableAllSprinklersByZoneId(zoneId);

        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Sprinklers disable in zone "+ zoneId);
        apiResponse.setData(disable);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
