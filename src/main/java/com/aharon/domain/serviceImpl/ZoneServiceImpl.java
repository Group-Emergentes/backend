package com.aharon.domain.serviceImpl;

import com.aharon.clients.service.ClientService;
import com.aharon.models.entities.Client;
import com.aharon.zones.model.entities.Zone;
import com.aharon.zones.dto.CreateZone;
import com.aharon.zones.dto.ZoneResponse;
import com.aharon.zones.repository.ZoneRepository;
import com.aharon.zones.service.ZoneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final ClientService clientService;

    @Override
    public ZoneResponse addZone(CreateZone createZone) {
        Client client = clientService.getClientById(createZone.getClientId());
        if(client == null){
            throw new IllegalArgumentException("Client not found");
        }
        Zone zone = new Zone(createZone, client);
        zone = zoneRepository.save(zone);
        return new ZoneResponse(zone);

    }

    @Override
    public Zone getZoneById(Long id) {
        return zoneRepository.findById(id).orElse(null);
    }

    @Override
    public List<ZoneResponse> getZonesByClientId(Long clientId) {

        return zoneRepository.findZonesByClientId(clientId).stream()
                .map(ZoneResponse::new).toList();
    }
}
