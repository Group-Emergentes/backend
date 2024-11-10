package com.aharon.sensors.service;

import com.aharon.sensors.dto.*;

import java.util.List;


public interface SensorService {

    SensorResponse addSensor(CreateSensor createSensor);

    void addSensorRecord(SensorRecordRequest sensorRecordRequest);

    List<SensorResponse> getAllSensorsByZone(Long zoneId);

    Boolean deleteSensor(String sensorId);

}
