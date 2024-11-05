package com.aharon.domain.serviceImpl;

import com.aharon.models.entities.HumidityHistory;
import com.aharon.models.entities.LatestSensorRegister;
import com.aharon.models.entities.Sensor;
import com.aharon.models.entities.TemperatureHistory;
import com.aharon.models.entities.Zone;
import com.aharon.models.valueobjets.SensorType;
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
    public void addSensorRecord(SensorRecordRequest sensorRecordRequest) {
        Sensor sensor = sensorRepository.findById(sensorRecordRequest.getSensorId())
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found (zepol.dev)"));

        if (sensor.getType() == SensorType.TEMPERATURE) {
            TemperatureHistory temperatureHistory = TemperatureHistory.builder()
                    .value(sensorRecordRequest.getValue())
                    .sensor(sensor)
                    .registerDate(new Date())
                    .zone(sensor.getZone())
                    .build();

            temperatureRegisterRepository.save(temperatureHistory);
            updateLatestSensorRegister(sensorRecordRequest.getSensorId(), sensorRecordRequest.getValue(), temperatureHistory);

        } else if (sensor.getType() == SensorType.HUMIDITY) {
            HumidityHistory humidityHistory = HumidityHistory.builder()
                    .value(sensorRecordRequest.getValue())
                    .sensor(sensor)
                    .registerDate(new Date())
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
    public List<LatestRecordsResponse> getAllLatestHumidityRegisters() {
        return latestSensorRegisterRepository.findAll().stream().map(
                LatestRecordsResponse::new
        ).toList();
    }

    @Override
    public LatestSensorRegister getLastHumidityRegister(String sensorId) {
        return null;
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

}
