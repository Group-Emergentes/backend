package com.aharon.sensors.controller;

import com.aharon.common.dto.ApiResponse;
import com.aharon.models.entities.LatestHumidityRegister;
import com.aharon.sensors.dto.CreateSensor;
import com.aharon.sensors.dto.HumidityRegister;
import com.aharon.sensors.dto.SensorResponse;
import com.aharon.sensors.service.SensorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @PostMapping("add-humidity-register")
    public ResponseEntity<ApiResponse<Boolean>> addHumidityRegister(@Valid @RequestBody HumidityRegister humidityRegister){
        Boolean result = sensorService.addNewHumidityRegister(humidityRegister);

        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage(result ? "Humidity data added successfully." : "Failed to add humidity data.");
        apiResponse.setData(result);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/last-humidity-record/{sensorId}")
    public ResponseEntity<ApiResponse<LatestHumidityRegister>> getLastHumidityRecord(@PathVariable String sensorId) {
        LatestHumidityRegister lastRecord = sensorService.getLastHumidityRegister(sensorId);

        ApiResponse<LatestHumidityRegister> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Latest humidity record fetched successfully.");
        apiResponse.setData(lastRecord);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
}
