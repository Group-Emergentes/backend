package com.aharon.config.websockets.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SensorRecordRequest {
    @NotNull
    private String sensorId;
    @NotNull
    private Double value;
}
