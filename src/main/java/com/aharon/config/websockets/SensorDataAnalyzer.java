package com.aharon.config.websockets;

import com.aharon.zones.model.entities.Zone;
import com.aharon.config.websockets.dto.SensorRecordRequest;

import java.util.DoubleSummaryStatistics;
import java.util.List;

public class SensorDataAnalyzer {

    private final Zone zone;
    public SensorDataAnalyzer(Zone zone) {
        this.zone = zone;
    }

    public boolean isReadingOutOfRange(SensorRecordRequest sensorRecord) {
        if (sensorRecord.getSensorId().equals("Tsensor-0001")) {
            return sensorRecord.getValue() < zone.getMinimumTemperature() ||
                    sensorRecord.getValue() > zone.getMaximumTemperature();
        }else{
            return sensorRecord.getValue() < zone.getMinimumHumidity() ||
                    sensorRecord.getValue() > zone.getMaximumHumidity();
        }
    }

    public double calculateAverage(List<SensorRecordRequest> sensorRecords) {
        DoubleSummaryStatistics stats = sensorRecords.stream()
                .filter(record -> !"Tsensor-0001".equals(record.getSensorId()))
                .mapToDouble(SensorRecordRequest::getValue)
                .summaryStatistics();

        return stats.getCount() == 0 ? 0.0 : stats.getAverage();
    }


    public boolean shouldActivateSprinklers(List<SensorRecordRequest> sensorRecords) {
        double average = calculateAverage(sensorRecords);
        return  average < zone.getMinimumHumidity();
    }

}

