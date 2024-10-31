package com.aharon.models.entities;

import com.aharon.zones.dto.CreateZone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name= "zones")
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

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

    public Zone(CreateZone createZone) {
        this.name = createZone.getName();
        this.cropType = createZone.getCropType();
        this.width = createZone.getWidth();
        this.length = createZone.getLength();
        this.minimumTemperature = createZone.getMinimumTemperature();
        this.maximumTemperature = createZone.getMaximumTemperature();
        this.minimumHumidity = createZone.getMinimumHumidity();
        this.maximumHumidity = createZone.getMaximumHumidity();
    }
}
