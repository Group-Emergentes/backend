package com.aharon.domain.serviceImpl;

import com.aharon.models.entities.HumidityHistory;
import com.aharon.models.entities.LatestSensorRegister;
import com.aharon.models.entities.Sensor;
import com.aharon.models.entities.TemperatureHistory;
import com.aharon.models.entities.Zone;
import com.aharon.sensors.dto.*;
import com.aharon.sensors.repository.HumidityHistoryRepository;
import com.aharon.sensors.repository.LatestSensorRegisterRepository;
import com.aharon.sensors.repository.SensorRepository;
import com.aharon.sensors.repository.TemperatureRegisterRepository;
import com.aharon.sensors.service.SensorService;
import com.aharon.zones.repository.ZoneRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


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
    public void addNewTemperatureRegister(TemperatureRegister temperatureRegister) {

        Sensor sensor = sensorRepository.findById (temperatureRegister.getSensorId())
                .orElseThrow(()-> new IllegalArgumentException("Sensor not found (zepol.dev)"));

        TemperatureHistory temperatureHistory = TemperatureHistory.builder()
                .value(temperatureRegister.getValue())
                .sensor(sensor)
                .registerDate(new Date())
                .zone(sensor.getZone())
                .build();

        temperatureRegisterRepository.save(temperatureHistory);

        // update register in LatestSensorRegister
        LatestSensorRegister latestRecord = latestSensorRegisterRepository.findById(temperatureRegister.getSensorId())
                .orElse(new LatestSensorRegister(temperatureHistory));
        latestRecord.setValue(temperatureRegister.getValue());
        latestSensorRegisterRepository.save(latestRecord);
    }

    @Transactional
    public void addNewHumidityRegister(HumidityRegister humidityRegister) {

        Sensor sensor = sensorRepository.findById (humidityRegister.getSensorId())
                .orElseThrow(()-> new IllegalArgumentException("Sensor not found (zepol.dev)"));

        HumidityHistory humidityHistory = HumidityHistory.builder()
                .value(humidityRegister.getValue())
                .sensor(sensor)
                .registerDate(new Date())
                .zone(sensor.getZone())
                .build();

        humidityHistoryRepository.save(humidityHistory);

        // update register in LatestSensorRegister
        LatestSensorRegister latestRecord = latestSensorRegisterRepository.findById(humidityRegister.getSensorId())
                .orElse(new LatestSensorRegister(humidityHistory));
        latestRecord.setValue(humidityRegister.getValue());
        latestSensorRegisterRepository.save(latestRecord);
    }

    @Override
    public List<LatestRecordsResponse> getAllLatestHumidityRegisters() {
        return latestSensorRegisterRepository.findAll().stream().map(
                LatestRecordsResponse::new
        ).toList();
    }

    @Override
    public LatestSensorRegister getLastHumidityRegister(String sensorId) {
        return null;
    }

}
