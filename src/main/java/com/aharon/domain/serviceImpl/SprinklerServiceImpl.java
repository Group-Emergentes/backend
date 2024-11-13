package com.aharon.domain.serviceImpl;

import com.aharon.sprinklers.dto.SprinklerActionRequest;
import com.aharon.sprinklers.model.Sprinkler;
import com.aharon.sprinklers.model.SprinklerActivationHistory;
import com.aharon.sprinklers.model.valueobjets.ActivationType;
import com.aharon.sprinklers.repository.SprinklerActivationHistoryRepository;
import com.aharon.zones.model.entities.Zone;
import com.aharon.sprinklers.dto.CreateSprinkler;
import com.aharon.sprinklers.dto.SprinklerResponse;
import com.aharon.sprinklers.repository.SprinklerRepository;
import com.aharon.sprinklers.service.SprinklerService;
import com.aharon.zones.repository.ZoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class SprinklerServiceImpl implements SprinklerService {

    private final ZoneRepository zoneRepository;
    private final SprinklerRepository sprinklerRepository;
    private final SprinklerActivationHistoryRepository sprinklerActivationHistoryRepository;

    @Override
    public SprinklerResponse addSprinkler(CreateSprinkler createSprinkler) {

        if (sprinklerRepository.existsSprinklerBySprinklerId(createSprinkler.getSprinklerId())) {
            throw new IllegalArgumentException("Sprinkler with the same ID already exists (zepol.dev)");
        }
        Zone zone = zoneRepository.findById(createSprinkler.getZoneId())
                .orElseThrow(() -> new IllegalArgumentException("Designated zone for sprinkler not found (zepol.dev)"));

        Sprinkler sprinkler = new Sprinkler(createSprinkler.getSprinklerId());
        sprinkler.setZone(zone);
        sprinklerRepository.save(sprinkler);

        return new SprinklerResponse(sprinkler);

    }

    @Override
    public Boolean deleteSprinkler(String sprinklerId) {
        try {
            sprinklerRepository.deleteById(sprinklerId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public List<SprinklerResponse> getAllSprinklersByZoneId(Long zoneId) {
        List<Sprinkler> sprinklers = sprinklerRepository.findAllByZone_Id(zoneId);

        return sprinklers.stream().map(SprinklerResponse::new).toList();
    }

    @Override
    @Transactional
    public Boolean activeAllSprinklersByZoneId(SprinklerActionRequest sprinklerActionRequest) {
        List<Sprinkler> sprinklers = sprinklerRepository.findAllByZone_Id(sprinklerActionRequest.getZoneId());
        boolean allActive = true;

        Date activationDate = new Date();

        for (Sprinkler sprinkler : sprinklers) {
            if (sprinkler.getActive()) continue;

            allActive = false;
            sprinkler.setActive(true);
            sprinkler.setLastActivation(activationDate);

            sprinkler.setIsManualActivated(sprinklerActionRequest.getIsManual());
            sprinkler.setIsAutomaticActivated(!sprinklerActionRequest.getIsManual());
        }

        if (allActive) return false;

        sprinklerRepository.saveAll(sprinklers);
        return true;
    }

    @Override
    @Transactional
    public Boolean disableAllSprinklersByZoneId(SprinklerActionRequest sprinklerActionRequest) {
        List<Sprinkler> sprinklers = sprinklerRepository.findAllByZone_Id(sprinklerActionRequest.getZoneId());
        if (sprinklers.isEmpty()) return false;

        boolean isManual = sprinklerActionRequest.getIsManual();
        Date nowDate = new Date();

        if (!isManual && nowDate.getTime() - sprinklers.get(0).getLastActivation().getTime()  < 180000) {
            return false;
        }

        List<SprinklerActivationHistory> activationHistoryList = new ArrayList<>();
        boolean anyActive = false;

        ActivationType activationType;

        for (Sprinkler sprinkler : sprinklers) {
            if (!sprinkler.getActive()) continue;

            activationType = sprinkler.getIsAutomaticActivated() ? ActivationType.AUTOMATIC : ActivationType.MANUAL;
            activationHistoryList.add(new SprinklerActivationHistory(sprinkler, activationType));

            sprinkler.setActive(false);
            sprinkler.setIsManualActivated(false);
            sprinkler.setIsAutomaticActivated(false);
            anyActive = true;
        }

        if (!anyActive) return false;

        if (!activationHistoryList.isEmpty()) {
            sprinklerActivationHistoryRepository.saveAll(activationHistoryList);
        }
        sprinklerRepository.saveAll(sprinklers);

        return true;
    }

}

