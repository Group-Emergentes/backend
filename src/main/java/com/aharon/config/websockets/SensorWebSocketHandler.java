package com.aharon.config.websockets;

import com.aharon.models.entities.Sensor;
import com.aharon.sensors.dto.SensorRecordRequest;
import com.aharon.sensors.service.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class SensorWebSocketHandler extends TextWebSocketHandler {

    private final SensorService sensorService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    public SensorWebSocketHandler(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode alerts = mapper.createObjectNode();
        ObjectNode response = mapper.createObjectNode();

        try {
            List<SensorRecordRequest> sensorRecordRequestList = Arrays.asList(
                    objectMapper.readValue(payload, SensorRecordRequest[].class)
            );

            List<SensorRecordRequest> humidityRecords = sensorRecordRequestList.stream()
                    .filter(record -> !record.getSensorId().equals("sensor-0001"))
                    .collect(Collectors.toList());

            Sensor sensor = sensorService.getBySensorId(sensorRecordRequestList.get(0).getSensorId());
            SensorDataAnalyzer analyzer = new SensorDataAnalyzer(sensor.getZone());

            for (SensorRecordRequest sensorRecord : sensorRecordRequestList) {
                sensorService.addSensorRecord(sensorRecord);

                if (analyzer.isReadingOutOfRange(sensorRecord)) {
                    alerts.put(sensorRecord.getSensorId(),
                            "ALERT: Sensor " + sensorRecord.getSensorId() + " has an irregular reading. Value: " + sensorRecord.getValue());
                }
            }

            if (alerts.size() > 0) {
                response.put("message", "Some sensors have irregular readings");
            } else {
                response.put("message", "All values within optimal range");
            }

            response.putPOJO("dataReceived", sensorRecordRequestList);

            double avgHumidity = analyzer.calculateAverage(humidityRecords);
            boolean activateSprinklers = analyzer.shouldActivateSprinklers(avgHumidity);

            response.put("activeSprinklers", activateSprinklers);

            // Enviar respuesta a todas las sesiones activas
            TextMessage broadcastMessage = new TextMessage(
                    mapper.createObjectNode()
                            .putPOJO("alerts", alerts)
                            .putPOJO("response", response)
                            .toString()
            );

            for (WebSocketSession activeSession : sessions) {
                if (activeSession.isOpen()) {
                    activeSession.sendMessage(broadcastMessage);
                }
            }

        } catch (Exception e) {
            response.put("message", "Error processing sensor data: " + e.getMessage());
            response.put("activeSprinklers", false);
            TextMessage errorMessage = new TextMessage(response.toString());

            // Enviar mensaje de error a todas las sesiones activas
            for (WebSocketSession activeSession : sessions) {
                if (activeSession.isOpen()) {
                    activeSession.sendMessage(errorMessage);
                }
            }
        }
    }
}
