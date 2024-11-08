package com.aharon.config.websockets;

import com.aharon.models.entities.Zone;
import com.aharon.sensors.dto.SensorRecordRequest;

import java.util.List;

public class SensorDataAnalyzer {

    private Zone zone;
    public SensorDataAnalyzer(Zone zone) {
        this.zone = zone;
    }

    public boolean isReadingOutOfRange(SensorRecordRequest sensorRecord) {
        if (sensorRecord.getSensorId().equals("sensor-0001")) {
            return sensorRecord.getValue() < zone.getMinimumTemperature() ||
                    sensorRecord.getValue() > zone.getMaximumTemperature();
        }else{
            return sensorRecord.getValue() < zone.getMinimumHumidity() ||
                    sensorRecord.getValue() > zone.getMaximumHumidity();
        }
    }

    public double calculateAverage(List<SensorRecordRequest> sensorRecords) {

        double total = sensorRecords.stream()
                .mapToDouble(SensorRecordRequest::getValue)
                .sum();

        return sensorRecords.isEmpty() ? 0.0 : total / sensorRecords.size();
    }

    public boolean shouldActivateSprinklers(List<SensorRecordRequest> sensorRecords) {
        double average = calculateAverage(sensorRecords);
        return  average < zone.getMinimumHumidity();
    }

}

