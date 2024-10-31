package com.aharon.domain.serviceImpl;

import com.aharon.models.entities.Sprinkler;
import com.aharon.sprinklers.dto.CreateSprinkler;
import com.aharon.sprinklers.dto.SprinklerResponse;
import com.aharon.sprinklers.repository.SprinklerRepository;
import com.aharon.sprinklers.service.SprinklerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SprinklerServiceImpl implements SprinklerService {
    private final SprinklerRepository sprinklerRepository;

    @Override
    public SprinklerResponse addSprinkler(CreateSprinkler createSprinkler) {

        Sprinkler sprinkler = new Sprinkler(createSprinkler);
        sprinkler=sprinklerRepository.save(sprinkler);
        return new SprinklerResponse(sprinkler);

    }
}
