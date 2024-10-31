package com.aharon.sensors.service;

import com.aharon.sensors.dto.CreateSensor;
import com.aharon.sensors.dto.SensorResponse;


public interface SensorService {
    SensorResponse addSensor(CreateSensor createSensor);
}
