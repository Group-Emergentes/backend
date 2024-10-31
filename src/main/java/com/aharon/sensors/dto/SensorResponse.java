package com.aharon.sensors.dto;

import com.aharon.models.entities.Sensor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SensorResponse {

    private Long id;
    private String sensorId;
    private Float value;
    private String type;
    private Long zoneId;
    private LocalDateTime lastConnection;

    public SensorResponse(Sensor sensor) {
        this.id = sensor.getId();
        this.sensorId = sensor.getSensorId();
        this.value = sensor.getValue();
        this.type = sensor.getType();
        this.zoneId = sensor.getZoneId();
        this.lastConnection = sensor.getLastConnection();
    }
}
