package com.aharon.sensors.dto;

import com.aharon.sensors.model.valueobjets.SensorType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSensor {

    @NotNull
    private String sensorId;
    @NotNull
    private SensorType type;
    @NotNull
    private Long zoneId;

}
