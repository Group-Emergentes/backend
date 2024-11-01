package com.aharon.sensors.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HumidityRegister {
    @NotNull
    private String sensorId;
    @NotNull
    private Double value;
    @NotNull
    private Long zoneId;
}
