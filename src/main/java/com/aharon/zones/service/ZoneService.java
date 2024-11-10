package com.aharon.zones.service;

import com.aharon.zones.model.entities.Zone;
import com.aharon.zones.dto.CreateZone;
import com.aharon.zones.dto.ZoneResponse;

import java.util.List;

public interface ZoneService {

    ZoneResponse addZone(CreateZone createZone);
    Zone getZoneById(Long id);
    List<ZoneResponse> getZonesByClientId(Long clientId);

}
