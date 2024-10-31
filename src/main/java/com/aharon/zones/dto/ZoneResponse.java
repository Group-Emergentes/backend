package com.aharon.zones.dto;

import com.aharon.models.entities.Zone;
import lombok.Data;

@Data
public class ZoneResponse {

    private Long id ;
    private String name;
    private String cropType;
    private Float width;
    private Float length;
    private Float minimumTemperature;
    private Float maximumTemperature;
    private Float minimumHumidity;
    private Float maximumHumidity;

    public ZoneResponse(Zone zone){
        this.id = zone.getId();
        this.name = zone.getName();
        this.cropType = zone.getCropType();
        this.width = zone.getWidth();
        this.length = zone.getLength();
        this.minimumTemperature = zone.getMinimumTemperature();
        this.maximumTemperature = zone.getMaximumTemperature();
        this.minimumHumidity = zone.getMinimumHumidity();
        this.maximumHumidity = zone.getMaximumHumidity();
    }
}
