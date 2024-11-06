package com.aharon.config.websockets;

import com.aharon.sensors.dto.SensorRecordRequest;
import com.aharon.sensors.service.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SensorWebSocketHandler extends TextWebSocketHandler {

    private final SensorService sensorService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SensorWebSocketHandler(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    double optimalTemperature = 25.0;
    double optimalHumidity = 50.0;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder alerts = new StringBuilder();
        ObjectNode response = mapper.createObjectNode();

        SensorDataAnalyzer analyzer = new SensorDataAnalyzer(optimalTemperature, optimalHumidity);

        try {
            List<SensorRecordRequest> sensorRecordRequestList = Arrays.asList(
                    objectMapper.readValue(payload, SensorRecordRequest[].class)
            );

            List<SensorRecordRequest> humidityRecords = sensorRecordRequestList.stream()
                    .filter(record -> !record.getSensorId().equals("sensor-0001"))
                    .collect(Collectors.toList());

            for (SensorRecordRequest sensorRecord : sensorRecordRequestList) {
                sensorService.addSensorRecord(sensorRecord);

                if (analyzer.isReadingOutOfRange(sensorRecord)) {
                    alerts.append("ALERT: Sensor " + sensorRecord.getSensorId() + " has an irregular reading. Value: " + sensorRecord.getValue() + "\n");
                }
            }
            if (alerts.length() > 0) {
                response.put("message", alerts.toString());
                session.sendMessage(new TextMessage(response.toString()));
            } else {
                response.put("message", "All values within optimal range");
                session.sendMessage(new TextMessage(response.toString()));
            }

            double avgHumidity = analyzer.calculateOptimalAverage(humidityRecords);
            boolean activateSprinklers = analyzer.shouldActivateSprinklers(optimalTemperature, avgHumidity);

            response.put("message", activateSprinklers ? "ALERT: Sprinklers activated" : "All values within optimal range");
            response.put("activeSprinklers", activateSprinklers);

        } catch (Exception e) {
            response.put("message", "Error processing sensor data: " + e.getMessage());
            response.put("activeSprinklers", false);
            session.sendMessage(new TextMessage(response.toString()));
        }
    }
}
