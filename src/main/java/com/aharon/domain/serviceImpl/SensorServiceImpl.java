package com.aharon.domain.serviceImpl;

import com.aharon.models.entities.Sensor;
import com.aharon.models.entities.TemperatureHistory;
import com.aharon.models.entities.Zone;
import com.aharon.sensors.dto.CreateSensor;
import com.aharon.sensors.dto.SensorResponse;
import com.aharon.sensors.dto.TemperatureRegister;
import com.aharon.sensors.repository.SensorRepository;
import com.aharon.sensors.repository.TemperatureRegisterRepository;
import com.aharon.sensors.service.SensorService;
import com.aharon.zones.repository.ZoneRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final ZoneRepository zoneRepository;
    private final SensorRepository sensorRepository;
    private final TemperatureRegisterRepository temperatureRegisterRepository;


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
    public Boolean addNewRegister(TemperatureRegister temperatureRegister) {

        if (!sensorRepository.existsSensorBySensorId (temperatureRegister.getSensorId())) {
            throw new IllegalArgumentException("Sensor not found (zepol.dev)");
        }
        zoneRepository.findById(temperatureRegister.getZoneId())
                .orElseThrow(() -> new IllegalArgumentException("Zone not found (zepol.dev)"));

        TemperatureHistory temperatureHistory = new TemperatureHistory(temperatureRegister);

        temperatureRegisterRepository.save(temperatureHistory);
        return true;

    }


}
