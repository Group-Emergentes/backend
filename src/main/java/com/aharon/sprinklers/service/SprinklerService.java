package com.aharon.sprinklers.service;

import com.aharon.sprinklers.dto.CreateSprinkler;
import com.aharon.sprinklers.dto.SprinklerResponse;
import com.aharon.zones.dto.CreateZone;
import com.aharon.zones.dto.ZoneResponse;

public interface SprinklerService {
    SprinklerResponse addSprinkler(CreateSprinkler createSprinkler);
}
