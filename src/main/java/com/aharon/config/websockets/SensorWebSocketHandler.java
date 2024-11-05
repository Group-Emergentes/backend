package com.aharon.config.websockets;

import com.aharon.sensors.dto.SensorRecordRequest;
import com.aharon.sensors.service.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Arrays;
import java.util.List;

public class SensorWebSocketHandler extends TextWebSocketHandler {

    private final SensorService sensorService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SensorWebSocketHandler(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        try {
            List<SensorRecordRequest> sensorRecordRequestList = Arrays.asList(
                    objectMapper.readValue(payload, SensorRecordRequest[].class)
            );
            for (SensorRecordRequest sensorRecordRequest : sensorRecordRequestList) {
                sensorService.addSensorRecord (sensorRecordRequest);
            }
            session.sendMessage(new TextMessage("All humidity records were received and processed correctly. "));

        } catch (Exception e) {
            session.sendMessage(new TextMessage("Error processing humidity log: " + e.getMessage()));
        }
    }
}
