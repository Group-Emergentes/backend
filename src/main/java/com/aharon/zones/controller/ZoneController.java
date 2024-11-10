package com.aharon.zones.controller;

import com.aharon.common.dto.ApiResponse;
import com.aharon.zones.dto.CreateZone;
import com.aharon.sensors.dto.SoilMoistureReport;
import com.aharon.zones.dto.ZoneResponse;
import com.aharon.sensors.service.AnalyticsService;
import com.aharon.zones.service.ZoneService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("{zoneId}/soil-moisture-report")
    public ResponseEntity<ApiResponse<SoilMoistureReport>> getTemperatureMonitoring(@PathVariable Long zoneId){
        SoilMoistureReport soilMoistureReport = analyticsService.getSoilMoistureReportByZoneId(zoneId);

        ApiResponse<SoilMoistureReport> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Analysis Soil Moisture Completed");
        apiResponse.setData(soilMoistureReport);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("client/{clientId}")
    public ResponseEntity<ApiResponse<List<ZoneResponse>>> getZone(@PathVariable Long clientId){
        List<ZoneResponse> zoneList= zoneService.getZonesByClientId(clientId);

        ApiResponse<List<ZoneResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("All zones of client "+ clientId +" are available");
        apiResponse.setData(zoneList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
