package com.aharon.domain.serviceImpl;

import com.aharon.models.entities.HumidityHistory;
import com.aharon.models.entities.LatestSensorRegister;
import com.aharon.sensors.model.entities.Sensor;
import com.aharon.models.entities.TemperatureHistory;
import com.aharon.zones.model.entities.Zone;
import com.aharon.sensors.model.valueobjets.SensorType;
import com.aharon.sensors.dto.*;
import com.aharon.sensors.repository.HumidityHistoryRepository;
import com.aharon.sensors.repository.LatestSensorRegisterRepository;
import com.aharon.sensors.repository.SensorRepository;
import com.aharon.sensors.repository.TemperatureRegisterRepository;
import com.aharon.sensors.service.SensorService;
import com.aharon.zones.repository.ZoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final ZoneRepository zoneRepository;
    private final SensorRepository sensorRepository;
    private final TemperatureRegisterRepository temperatureRegisterRepository;
    private final HumidityHistoryRepository humidityHistoryRepository;
    private final LatestSensorRegisterRepository latestSensorRegisterRepository;

    @Override
    public SensorResponse addSensor(CreateSensor createSensor) {

        if (sensorRepository.existsSensorBySensorId(createSensor.getSensorId())) {
            throw new IllegalArgumentException("Sensor with the same ID already exists (zepol.dev)");
        }

        Zone zone = zoneRepository.findById(createSensor.getZoneId())
                .orElseThrow(() -> new IllegalArgumentException("Designated zone for sensor not found (zepol.dev)"));

        Sensor sensor = new Sensor(createSensor);
        sensor.setZone(zone);
        sensor = sensorRepository.save(sensor);

        return new SensorResponse(sensor);
    }

    @Override
    public void addSensorRecord(SensorRecordRequest sensorRecordRequest) {
        Sensor sensor = sensorRepository.findById(sensorRecordRequest.getSensorId())
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found (zepol.dev)"));

        if (sensor.getType() == SensorType.TEMPERATURE) {
            TemperatureHistory temperatureHistory = TemperatureHistory.builder()
                    .value(sensorRecordRequest.getValue())
                    .sensor(sensor)
                    .registerDate(generateRandomDate())
                    .zone(sensor.getZone())
                    .build();

            temperatureRegisterRepository.save(temperatureHistory);
            updateLatestSensorRegister(sensorRecordRequest.getSensorId(), sensorRecordRequest.getValue(), temperatureHistory);

        } else if (sensor.getType() == SensorType.HUMIDITY) {
            HumidityHistory humidityHistory = HumidityHistory.builder()
                    .value(sensorRecordRequest.getValue())
                    .sensor(sensor)
                    .registerDate(generateRandomDate())
                    .zone(sensor.getZone())
                    .build();

            humidityHistoryRepository.save(humidityHistory);
            updateLatestSensorRegister(sensorRecordRequest.getSensorId(), sensorRecordRequest.getValue(), humidityHistory);
        }else{
            throw new IllegalArgumentException("History type not supported");
        }

        sensor.setLastConnection(new Date());
        sensorRepository.save(sensor);
    }


    @Override
    public List<SensorResponse> getAllSensorsByZone(Long zoneId) {
        List<Sensor> sensors = sensorRepository.findAllByZone_Id(zoneId);

        List<LatestSensorRegister> latestRegisters = latestSensorRegisterRepository.findBySensorIdIn(
                sensors.stream().map(Sensor::getSensorId).toList()
        );

        Map<String, Double> sensorValuesMap = latestRegisters.stream()
                .collect(Collectors.toMap(LatestSensorRegister::getSensorId, LatestSensorRegister::getValue));

        return sensors.stream().map(sensor -> {
                    SensorResponse response = new SensorResponse(sensor);
                    response.setValue(sensorValuesMap.getOrDefault(sensor.getSensorId(), null));
                    return response;
                }).toList();
    }

    @Override
    public Boolean deleteSensor(String sensorId) {
        try {
            sensorRepository.deleteById(sensorId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }









    private void updateLatestSensorRegister(String sensorId, Double value, Object history) {
        LatestSensorRegister latestRecord;

        if (history instanceof HumidityHistory) {
            latestRecord = latestSensorRegisterRepository.findById(sensorId)
                    .orElse(new LatestSensorRegister((HumidityHistory) history));
        } else if (history instanceof TemperatureHistory) {
            latestRecord = latestSensorRegisterRepository.findById(sensorId)
                    .orElse(new LatestSensorRegister((TemperatureHistory) history));
        } else {
            throw new IllegalArgumentException("History type not supported");
        }

        latestRecord.setValue(value);
        latestSensorRegisterRepository.save(latestRecord);
    }

    private static Date generateRandomDate() {

        long startMillis = new Date(2024 - 1900, 0, 1).getTime();
        long endMillis = new Date().getTime();

        long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis);
        return new Date(randomMillis);
    }

}
