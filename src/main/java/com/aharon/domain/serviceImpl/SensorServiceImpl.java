package com.aharon.domain.serviceImpl;

import com.aharon.models.entities.Sensor;
import com.aharon.models.entities.Zone;
import com.aharon.sensors.dto.CreateSensor;
import com.aharon.sensors.dto.SensorResponse;
import com.aharon.sensors.repository.SensorRepository;
import com.aharon.sensors.service.SensorService;
import com.aharon.zones.dto.CreateZone;
import com.aharon.zones.dto.ZoneResponse;
import com.aharon.zones.repository.ZoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final ZoneRepository zoneRepository;

    private final SensorRepository sensorRepository;


    @Override
    public SensorResponse addSensor(CreateSensor createSensor) {
        Sensor sensor=new Sensor(createSensor);
        sensor= sensorRepository.save(sensor);
        return new SensorResponse(sensor);
    }
}
