package com.aharon.sensors.service;

import com.aharon.models.entities.LatestSensorRegister;
import com.aharon.sensors.dto.*;

import java.util.List;


public interface SensorService {

    SensorResponse addSensor(CreateSensor createSensor);

    void addNewTemperatureRegister(TemperatureRegister temperatureRegister);

    void addNewHumidityRegister(HumidityRegister humidityRegister);

    List<LatestRecordsResponse> getAllLatestHumidityRegisters();

    LatestSensorRegister getLastHumidityRegister(String sensorId);


}
