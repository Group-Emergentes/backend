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

    @PostMapping("add-temperature-register")
    public ResponseEntity<ApiResponse<Boolean>> addRegister(
            @Valid @RequestBody List<TemperatureRegister> temperatureRegisterList){

        boolean allSuccess = true;
        int successCount = 0;
        int failureCount = 0;
        StringBuilder messageBuilder = new StringBuilder();

        for (TemperatureRegister temperatureRegister : temperatureRegisterList) {
            try {
                sensorService.addNewTemperatureRegister(temperatureRegister);
                successCount++;
                messageBuilder.append("Record successfully added for the sensor: ")
                        .append(temperatureRegister.getSensorId())
                        .append("\n");
            } catch (Exception e) {
                allSuccess = false;
                failureCount++;
                messageBuilder.append("Error adding record for sensor: ")
                        .append(temperatureRegister.getSensorId())
                        .append(" - Error: ")
                        .append(e.getMessage())
                        .append("\n");
            }
        }

        return getApiResponseResponseEntity(allSuccess, successCount, failureCount, messageBuilder);
    }

    @PostMapping("add-humidity-register")
    public ResponseEntity<ApiResponse<Boolean>> addHumidityRegister(
            @Valid @RequestBody List<HumidityRegister> humidityRegisterList){

        boolean allSuccess = true;
        int successCount = 0;
        int failureCount = 0;
        StringBuilder messageBuilder = new StringBuilder();

        for(HumidityRegister humidityRegister: humidityRegisterList){
            try{
                sensorService.addNewHumidityRegister(humidityRegister);
                successCount++;
                messageBuilder.append("Record successfully added for sensor: ")
                        .append(humidityRegister.getSensorId())
                        .append("\n");
            }catch(Exception e){
                allSuccess = false;
                failureCount ++;
                messageBuilder.append("Error adding record for sensor: ")
                        .append(humidityRegister.getSensorId())
                        .append(" - Error: ")
                        .append(e.getMessage())
                        .append("\n");
            }
        }

        return getApiResponseResponseEntity(allSuccess, successCount, failureCount, messageBuilder);
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
