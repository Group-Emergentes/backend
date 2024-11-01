package com.aharon.sensors.service;

import com.aharon.models.entities.LatestHumidityRegister;
import com.aharon.sensors.dto.CreateSensor;
import com.aharon.sensors.dto.HumidityRegister;
import com.aharon.sensors.dto.SensorResponse;
import com.aharon.sensors.dto.TemperatureRegister;


public interface SensorService {
    SensorResponse addSensor(CreateSensor createSensor);
    Boolean addNewRegister(TemperatureRegister temperatureRegister);

    Boolean addNewHumidityRegister(HumidityRegister humidityRegister);
    LatestHumidityRegister getLastHumidityRegister(String sensorId);

}
