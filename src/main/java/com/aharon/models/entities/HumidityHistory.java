package com.aharon.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "humidity_history")
public class HumidityHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Date registerDate = generateRandomDate();

    @NotNull
    private Double value;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;


    private static Date generateRandomDate() {

        long startMillis = new Date(2024 - 1900, 0, 1).getTime();
        long endMillis = new Date().getTime();

        long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis);
        return new Date(randomMillis);
    }
}
