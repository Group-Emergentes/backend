package com.aharon.sensors.dto;

import com.aharon.models.entities.LatestSensorRegister;
import lombok.Data;

import java.util.Date;

@Data
public class LatestRecordsResponse {

    private String sensorId;
    private Date registerDate;
    private Double value;
    private Long zoneId;

    public LatestRecordsResponse(LatestSensorRegister latestSensorRegister){
        this.sensorId = latestSensorRegister.getSensorId();
        this.registerDate = latestSensorRegister.getRegisterDate();
        this.value = latestSensorRegister.getValue();
        this.zoneId = latestSensorRegister.getZone().getId();
    }
}
