package com.aharon.config.websockets;

import com.aharon.sensors.dto.SensorRecordRequest;

import java.util.List;
import java.util.stream.Collectors;

public class SensorDataAnalyzer {

    private double optimalTemperature;
    private double optimalHumidity;
    private double deviationThreshold = 5.0;

    public SensorDataAnalyzer(double optimalTemperature, double optimalHumidity) {
        this.optimalTemperature = optimalTemperature;
        this.optimalHumidity = optimalHumidity;
    }

    public boolean isReadingOutOfRange(SensorRecordRequest sensorRecord) {

        /*if (sensorRecord.getSensorId().equals("sensor-0001")) {
            return Math.abs(sensorRecord.getValue() - optimalTemperature) > deviationThreshold;
        } else if (sensorRecord.getSensorId().equals("sensor-0002")) {
            return Math.abs(sensorRecord.getValue() - optimalHumidity) > deviationThreshold;
        }*/
        return false;
    }

    public double calculateOptimalAverage(List<SensorRecordRequest> sensorRecords) {
        List<SensorRecordRequest> validRecords = sensorRecords.stream()
                .filter(record -> !isReadingOutOfRange(record))
                .collect(Collectors.toList());

        double total = validRecords.stream()
                .mapToDouble(SensorRecordRequest::getValue)
                .sum();

        return validRecords.isEmpty() ? 0.0 : total / validRecords.size();
    }

    public boolean shouldActivateSprinklers(double averageTemperature, double averageHumidity) {
        return averageTemperature > optimalTemperature || averageHumidity > optimalHumidity;
    }
}

