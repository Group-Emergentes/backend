package com.aharon.sensors.dto;

import com.aharon.models.entities.Sensor;
import lombok.Data;

import java.util.Date;

@Data
public class SensorResponse {

    private String sensorId;
    private String type;
    private Date lastConnection;
    private Long zoneId;
    private Float value = null;

    public SensorResponse(Sensor sensor){
        this.sensorId = sensor.getSensorId();
        this.type = sensor.getType();
        this.lastConnection = sensor.getLastConnection();
        this.zoneId = sensor.getZone().getId();
    }

}
