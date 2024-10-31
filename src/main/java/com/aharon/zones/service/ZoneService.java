package com.aharon.zones.service;

import com.aharon.zones.dto.CreateZone;
import com.aharon.zones.dto.ZoneResponse;

public interface ZoneService {

    ZoneResponse addZone(CreateZone createZone);

}
