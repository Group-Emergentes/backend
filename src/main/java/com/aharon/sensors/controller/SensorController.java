package com.aharon.sensors.controller;

import com.aharon.common.dto.ApiResponse;
import com.aharon.sensors.dto.*;
import com.aharon.sensors.service.SensorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/sensors")
@AllArgsConstructor
public class SensorController {
    private final SensorService sensorService;

    @PostMapping
    public ResponseEntity<ApiResponse<SensorResponse>> addSensor(@Valid @RequestBody CreateSensor createSensor) {
        SensorResponse sensorResponse = sensorService.addSensor(createSensor);

        ApiResponse<SensorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Sensor created successfully.");
        apiResponse.setData(sensorResponse);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/last-records")
    public ResponseEntity<ApiResponse<List<LatestRecordsResponse>>> getAllLatestHumidityRegisters() {
        List<LatestRecordsResponse> latestRegisters = sensorService.getAllLatestHumidityRegisters();

        ApiResponse<List<LatestRecordsResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Latest humidity records fetched successfully.");
        apiResponse.setData(latestRegisters);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    private ResponseEntity<ApiResponse<Boolean>> getApiResponseResponseEntity(boolean allSuccess, int successCount, int failureCount, StringBuilder messageBuilder) {
        messageBuilder.insert(0, String.format("Resume: %d successful registrations, %d failed.\n", successCount, failureCount));

        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(allSuccess);
        apiResponse.setMessage(messageBuilder.toString());
        apiResponse.setData(successCount > 0);

        HttpStatus status = allSuccess ? HttpStatus.CREATED : (successCount > 0 ? HttpStatus.MULTI_STATUS : HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(apiResponse, status);
    }
}
