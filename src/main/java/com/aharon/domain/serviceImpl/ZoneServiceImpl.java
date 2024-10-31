package com.aharon.domain.serviceImpl;

import com.aharon.models.entities.Zone;
import com.aharon.zones.dto.CreateZone;
import com.aharon.zones.dto.ZoneResponse;
import com.aharon.zones.repository.ZoneRepository;
import com.aharon.zones.service.ZoneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;

    @Override
    public ZoneResponse addZone(CreateZone createZone) {

        Zone zone = new Zone(createZone);
        zone = zoneRepository.save(zone);
        return new ZoneResponse(zone);

    }
}
