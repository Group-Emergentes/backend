package com.aharon.zones.service;


import com.aharon.zones.dto.TemperatureMonitoring;

public interface AnalyticsService {

    TemperatureMonitoring getTemperatureMonitoring(Long zoneId);

}
