package com.aharon.sensors.service;

import com.aharon.models.entities.LatestSensorRegister;
import com.aharon.sensors.dto.*;

import java.util.List;


public interface SensorService {

    SensorResponse addSensor(CreateSensor createSensor);

    void addSensorRecord(SensorRecordRequest sensorRecordRequest);

    List<LatestRecordsResponse> getAllLatestHumidityRegisters();

    LatestSensorRegister getLastHumidityRegister(String sensorId);


}
