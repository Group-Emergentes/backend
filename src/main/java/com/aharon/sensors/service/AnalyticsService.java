package com.aharon.sensors.service;


import com.aharon.models.entities.HumidityHistory;
import com.aharon.sensors.dto.SoilMoistureReport;

import java.util.List;

public interface AnalyticsService {

    SoilMoistureReport getSoilMoistureReportByZoneId(Long zoneId);


}
