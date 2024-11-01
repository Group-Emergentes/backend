package com.aharon.sensors.service;

import com.aharon.models.entities.LatestHumidityRegister;
import com.aharon.sensors.dto.CreateSensor;
import com.aharon.sensors.dto.HumidityRegister;
import com.aharon.sensors.dto.SensorResponse;
import java.util.List;
import com.aharon.sensors.dto.TemperatureRegister;


public interface SensorService {
    SensorResponse addSensor(CreateSensor createSensor);
    Boolean addNewRegister(TemperatureRegister temperatureRegister);

    Boolean addNewHumidityRegister(HumidityRegister humidityRegister);

    List<LatestHumidityRegister> getAllLatestHumidityRegisters();

    LatestHumidityRegister getLastHumidityRegister(String sensorId);


}
