package com.aharon.zones.service;


import com.aharon.zones.dto.SoilMoistureReport;

public interface AnalyticsService {

    SoilMoistureReport getSoilMoistureReport(Long zoneId);


}
