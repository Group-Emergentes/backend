package com.aharon.zones.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateZone {

    @NotNull
    private String name;
    @NotNull
    private String cropType;
    @NotNull
    private Float width;
    @NotNull
    private Float length;
    @NotNull
    private Float minimumTemperature;
    @NotNull
    private Float maximumTemperature;
    @NotNull
    private Float minimumHumidity;
    @NotNull
    private Float maximumHumidity;

}
