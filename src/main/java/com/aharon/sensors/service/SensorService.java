package com.aharon.sensors.service;

import com.aharon.sensors.dto.CreateSensor;
import com.aharon.sensors.dto.SensorResponse;
import com.aharon.sensors.dto.TemperatureRegister;


public interface SensorService {
    SensorResponse addSensor(CreateSensor createSensor);

    Boolean addNewRegister(TemperatureRegister temperatureRegister);
}
