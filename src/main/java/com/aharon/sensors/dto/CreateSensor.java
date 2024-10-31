package com.aharon.sensors.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSensor {

    @NotNull
    private String sensorId;
    @NotNull
    private String type;
    @NotNull
    private Long zoneId;

}
