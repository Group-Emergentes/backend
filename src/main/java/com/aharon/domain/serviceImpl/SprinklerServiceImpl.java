package com.aharon.domain.serviceImpl;

import com.aharon.models.entities.Sensor;
import com.aharon.models.entities.Sprinkler;
import com.aharon.models.entities.Zone;
import com.aharon.sensors.dto.SensorResponse;
import com.aharon.sprinklers.dto.CreateSprinkler;
import com.aharon.sprinklers.dto.SprinklerResponse;
import com.aharon.sprinklers.repository.SprinklerRepository;
import com.aharon.sprinklers.service.SprinklerService;
import com.aharon.zones.repository.ZoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SprinklerServiceImpl implements SprinklerService {

    private final ZoneRepository zoneRepository;
    private final SprinklerRepository sprinklerRepository;

    @Override
    public SprinklerResponse addSprinkler(CreateSprinkler createSprinkler) {

        if (sprinklerRepository.existsSprinklerBySprinklerId(createSprinkler.getSprinklerId())) {
            throw new IllegalArgumentException("Sprinkler with the same ID already exists (zepol.dev)");
        }
        Zone zone = zoneRepository.findById(createSprinkler.getZoneId())
                .orElseThrow(() -> new IllegalArgumentException("Designated zone for sprinkler not found (zepol.dev)"));

        Sprinkler sprinkler = new Sprinkler(createSprinkler);
        sprinkler.setZone(zone);
        sprinklerRepository.save(sprinkler);

        return new SprinklerResponse(sprinkler);

    }
}
