package com.aharon.config.websockets;

import com.aharon.models.entities.Sensor;
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

            session.sendMessage(new TextMessage(
                    mapper.createObjectNode()
                            .putPOJO("alerts", alerts)
                            .putPOJO("response", response)
                            .toString()
            ));

        } catch (Exception e) {
            response.put("message", "Error processing sensor data: " + e.getMessage());
            response.put("activeSprinklers", false);
            session.sendMessage(new TextMessage(response.toString()));
        }
    }
}
