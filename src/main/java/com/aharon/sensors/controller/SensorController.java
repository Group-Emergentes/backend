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

    @GetMapping("/{zoneId}")
    public ResponseEntity<ApiResponse<List<SensorResponse>>> getAllLatestRecords(
            @PathVariable("zoneId") Long zoneId){
        List<SensorResponse> sensorResponseList = sensorService.getAllSensorsByZone(zoneId);

        ApiResponse<List<SensorResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("All sensors for zne "+ zoneId);
        apiResponse.setData(sensorResponseList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("{sensorId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteSensor(@PathVariable("sensorId") String sensorId) {
        sensorService.deleteSensor(sensorId);

        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Deleted sensor "+sensorId);
        apiResponse.setData(true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
