package com.aharon.sprinklers.service;

import com.aharon.sprinklers.dto.SprinklerActionRequest;
import com.aharon.sprinklers.dto.CreateSprinkler;
import com.aharon.sprinklers.dto.SprinklerResponse;

import java.util.List;

public interface SprinklerService {

    SprinklerResponse addSprinkler(CreateSprinkler createSprinkler);

    Boolean deleteSprinkler(String sprinklerId);

    List<SprinklerResponse> getAllSprinklersByZoneId(Long zoneId);

    Boolean activeAllSprinklersByZoneId(SprinklerActionRequest sprinklerActionRequest);

    Boolean disableAllSprinklersByZoneId(SprinklerActionRequest sprinklerActionRequest);

}
