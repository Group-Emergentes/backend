package com.aharon.zones.controller;

import com.aharon.common.dto.ApiResponse;
import com.aharon.zones.dto.CreateZone;
import com.aharon.zones.dto.TemperatureMonitoring;
import com.aharon.zones.dto.ZoneResponse;
import com.aharon.zones.service.AnalyticsService;
import com.aharon.zones.service.ZoneService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zones")
@AllArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;
    private final AnalyticsService analyticsService;

    @PostMapping
    public ResponseEntity<ApiResponse<ZoneResponse>> addZone(@Valid @RequestBody CreateZone createZone) {
        ZoneResponse zoneResponse = zoneService.addZone(createZone);

        ApiResponse<ZoneResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Zone created successfully.");
        apiResponse.setData(zoneResponse);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("{zoneId}/temperature-monitoring")
    public ResponseEntity<ApiResponse<TemperatureMonitoring>> getTemperatureMonitoring(@PathVariable Long zoneId){
        TemperatureMonitoring temperatureMonitoring = analyticsService.getTemperatureMonitoring(zoneId);

        ApiResponse<TemperatureMonitoring> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Analysis Completed");
        apiResponse.setData(temperatureMonitoring);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
