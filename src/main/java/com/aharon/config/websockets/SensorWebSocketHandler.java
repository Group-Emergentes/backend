package com.aharon.config.websockets;

import com.aharon.models.entities.Notification;
import com.aharon.zones.model.entities.Zone;
import com.aharon.notifications.service.NotificationService;
import com.aharon.config.websockets.dto.SensorRecordRequest;
import com.aharon.sensors.service.SensorService;
import com.aharon.zones.service.ZoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriTemplate;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;


public class SensorWebSocketHandler extends TextWebSocketHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final SensorService sensorService;
    private final ZoneService zoneService;
    private final AlertWebSocketHandler alertWebSocketHandler;
    private final NotificationService notificationService;
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private boolean sprinklersSate = false;

    private Zone zone;

    public SensorWebSocketHandler(
            SensorService sensorService,
            ZoneService zoneService,
            AlertWebSocketHandler alertWebSocketHandler,
            NotificationService notificationService)
    {
        this.sensorService = sensorService;
        this.zoneService = zoneService;
        this.alertWebSocketHandler = alertWebSocketHandler;
        this.notificationService = notificationService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        UriTemplate template = new UriTemplate("/ws/register-sensor-data/{zoneId}");
        Map<String, String> parameters = template.match(session.getUri().getPath());
        Long zoneId = Long.parseLong(parameters.get("zoneId"));

        this.zone = zoneService.getZoneById(zoneId);

        if(!this.zone.getSprinklerList().isEmpty()){
            this.sprinklersSate = this.zone.getSprinklerList().get(0).getActive();
        }

        sessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ObjectNode response = objectMapper.createObjectNode();

        try {
            List<SensorRecordRequest> sensorRecords = Arrays.asList(
                    objectMapper.readValue(payload, SensorRecordRequest[].class));

            for (SensorRecordRequest sensorRecord : sensorRecords) {
                sensorService.addSensorRecord(sensorRecord);

                if (isReadingOutOfRange(sensorRecord)) {
                    Notification notification = new Notification();
                    notification.setZoneId(this.zone.getId());
                    notification.setSensorId(sensorRecord.getSensorId());
                    notification.setMessage("ALERT: Sensor " + sensorRecord.getSensorId() + " has an irregular reading. Value: " + sensorRecord.getValue());
                    notification.setTimestamp(new Date());
                    notificationService.createNotification(notification);

                    alertWebSocketHandler.broadcastAlerts(notification);
                }
            }

            response.put("message", "Data processed successfully.");
            response.set("sensorData", objectMapper.valueToTree(sensorRecords));
            response.put("actualSprinklersState", this.sprinklersSate);

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

    public void changeSprinklerStatus(boolean status){
        this.sprinklersSate = status;
    }


    private boolean isReadingOutOfRange(SensorRecordRequest sensorRecord) {
        if (sensorRecord.getSensorId().equals("Tsensor-0001")) {
            return sensorRecord.getValue() < this.zone.getMinimumTemperature() ||
                    sensorRecord.getValue() > this.zone.getMaximumTemperature();
        }else{
            return sensorRecord.getValue() < this.zone.getMinimumHumidity() ||
                    sensorRecord.getValue() > this.zone.getMaximumHumidity();
        }
    }
}
