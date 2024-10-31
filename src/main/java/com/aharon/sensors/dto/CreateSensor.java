package com.aharon.sensors.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateSensor {
    @NotNull
    private String sensorId;

    @NotNull
    private Float value;

    @NotNull
    private String type;

    @NotNull
    private Long zoneId;

    @NotNull
    private LocalDateTime lastConnection;
}
