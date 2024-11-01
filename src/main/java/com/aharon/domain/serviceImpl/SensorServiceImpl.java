package com.aharon.domain.serviceImpl;

import com.aharon.models.entities.HumidityHistory;
import com.aharon.models.entities.LatestHumidityRegister;
import com.aharon.models.entities.Sensor;
import com.aharon.models.entities.Zone;
import com.aharon.sensors.dto.CreateSensor;
import com.aharon.sensors.dto.HumidityRegister;
import com.aharon.sensors.dto.SensorResponse;
import com.aharon.sensors.repository.HumidityHistoryRepository;
import com.aharon.sensors.repository.LatestHumidityRegisterRepository;
import com.aharon.sensors.repository.SensorRepository;
import com.aharon.sensors.service.SensorService;
import com.aharon.zones.repository.ZoneRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
public class    SensorServiceImpl implements SensorService {

    private final ZoneRepository zoneRepository;
    private final SensorRepository sensorRepository;
    private final HumidityHistoryRepository humidityHistoryRepository;
    private final LatestHumidityRegisterRepository latestHumidityRegisterRepository;


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
    @Transactional
    public Boolean addNewHumidityRegister(HumidityRegister humidityRegister) {
        // Validation
        if (!sensorRepository.existsSensorBySensorId(humidityRegister.getSensorId())) {
            throw new IllegalArgumentException("Sensor not found");
        }
        zoneRepository.findById(humidityRegister.getZoneId())
                .orElseThrow(() -> new IllegalArgumentException("Zone not found"));

        HumidityHistory humidityHistory = new HumidityHistory(humidityRegister);
        humidityHistoryRepository.save(humidityHistory);

        // update register in LatestHumidityRegister
        LatestHumidityRegister latestRecord = latestHumidityRegisterRepository.findById(humidityRegister.getSensorId())
                .orElse(new LatestHumidityRegister());

        latestRecord.setSensorId(humidityRegister.getSensorId());
        latestRecord.setValue(humidityRegister.getValue());
        latestRecord.setZoneId(humidityRegister.getZoneId());
        latestRecord.setRegisterDate(new Date());

        latestHumidityRegisterRepository.save(latestRecord);

        return true;
    }

    @Override
    public List<LatestHumidityRegister> getAllLatestHumidityRegisters() {
        return latestHumidityRegisterRepository.findAll();
    }

}
