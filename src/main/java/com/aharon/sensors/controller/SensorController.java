package com.aharon.sensors.controller;

import com.aharon.common.dto.ApiResponse;
import com.aharon.sensors.dto.CreateSensor;
import com.aharon.sensors.dto.SensorResponse;
import com.aharon.sensors.service.SensorService;
import com.aharon.zones.dto.CreateZone;
import com.aharon.zones.dto.ZoneResponse;
import com.aharon.zones.service.ZoneService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
