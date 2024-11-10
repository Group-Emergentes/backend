package com.aharon.sprinklers.service;

import com.aharon.sprinklers.dto.ActiveRequest;
import com.aharon.sprinklers.dto.CreateSprinkler;
import com.aharon.sprinklers.dto.SprinklerResponse;

import java.util.List;

public interface SprinklerService {

    SprinklerResponse addSprinkler(CreateSprinkler createSprinkler);

    Boolean deleteSprinkler(String sprinklerId);

    List<SprinklerResponse> getAllSprinklersByZoneId(Long zoneId);

    Boolean activeAllSprinklersByZoneId(ActiveRequest activeRequest);

    Boolean disableAllSprinklersByZoneId(Long zoneId);

}
