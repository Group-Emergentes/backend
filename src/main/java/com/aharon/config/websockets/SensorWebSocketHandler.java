package com.aharon.config.websockets;

import com.aharon.models.entities.Zone;
import com.aharon.sensors.dto.SensorRecordRequest;
import com.aharon.sensors.service.SensorService;
import com.aharon.zones.service.ZoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class SensorWebSocketHandler extends TextWebSocketHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<Long, ObjectNode> alertCache = new ConcurrentHashMap<>();

    private final SensorService sensorService;
    private final ZoneService zoneService;
    private final AlertWebSocketHandler alertWebSocketHandler;
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private SensorDataAnalyzer analyzer;
    private long zoneId;

    public SensorWebSocketHandler(SensorService sensorService, ZoneService zoneService, AlertWebSocketHandler alertWebSocketHandler) {
        this.sensorService = sensorService;
        this.zoneService = zoneService;
        this.alertWebSocketHandler = alertWebSocketHandler;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        UriTemplate template = new UriTemplate("/ws/register-sensor-data/{zoneId}");
        Map<String, String> parameters = template.match(session.getUri().getPath());
        this.zoneId = Long.parseLong(parameters.get("zoneId"));

        Zone zone = zoneService.getZoneById(zoneId);
        this.analyzer = new SensorDataAnalyzer(zone);

        sessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ObjectNode alerts = objectMapper.createObjectNode();
        ObjectNode response = objectMapper.createObjectNode();

        try {
            List<SensorRecordRequest> sensorRecords = Arrays.asList(
                    objectMapper.readValue(payload, SensorRecordRequest[].class));

            for (SensorRecordRequest sensorRecord : sensorRecords) {
                sensorService.addSensorRecord(sensorRecord);
                if (analyzer.isReadingOutOfRange(sensorRecord)) {
                    alerts.put(sensorRecord.getSensorId(),
                            "ALERT: Sensor " + sensorRecord.getSensorId() + " has an irregular reading. Value: " + sensorRecord.getValue());
                }
            }

            if (alerts.size() > 0) {
                alertCache.put(zoneId, alerts);
                alertWebSocketHandler.broadcastAlerts(alerts, zoneId);
            }

            response.put("message", alerts.size() > 0 ? "Some sensors have irregular readings" : "All values within optimal range");
            response.putPOJO("dataReceived", sensorRecords);
            response.put("activeSprinklers", analyzer.shouldActivateSprinklers(sensorRecords));

            TextMessage broadcastMessage = new TextMessage(response.toString());
            for (WebSocketSession activeSession : sessions) {
                if (activeSession.isOpen()) {
                    activeSession.sendMessage(broadcastMessage);
                }
            }

        } catch (Exception e) {
            response.put("message", "Error processing sensor data: " + e.getMessage());
            TextMessage errorMessage = new TextMessage(response.toString());
            for (WebSocketSession activeSession : sessions) {
                if (activeSession.isOpen()) {
                    activeSession.sendMessage(errorMessage);
                }
            }
        }
    }

    public static ObjectNode getAlertCache(Long zoneId) {
        return alertCache.get(zoneId);
    }
}
