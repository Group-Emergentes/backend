package com.aharon.config.websockets;

import com.aharon.models.entities.Notification;
import com.aharon.notifications.service.NotificationService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class AlertWebSocketHandler extends TextWebSocketHandler {

    private final NotificationService notificationService;
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final Map<WebSocketSession, Long> sessionZoneMap = new ConcurrentHashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public AlertWebSocketHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        Long zoneId = extractZoneId(session);
        sessionZoneMap.put(session, zoneId);

        sendCurrentAlerts(session);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        sessionZoneMap.remove(session);
    }

    private void sendCurrentAlerts(WebSocketSession session) throws Exception {
        List<Notification> alerts = notificationService.getAllNotifications();
        for (Notification alert : alerts) {
            ObjectNode alertMessage = objectMapper.createObjectNode();
            alertMessage.put("sensorId", alert.getSensorId());
            alertMessage.put("message", alert.getMessage());
            session.sendMessage(new TextMessage(alertMessage.toString()));
        }
    }

    public void broadcastAlerts(Notification alert) throws Exception {
        ObjectNode alertMessage = objectMapper.createObjectNode();
        alertMessage.put("sensorId", alert.getSensorId());
        alertMessage.put("message", alert.getMessage());
        TextMessage textMessage = new TextMessage(alertMessage.toString());

        for (WebSocketSession session : sessions) {
            if (session.isOpen() && sessionZoneMap.get(session).equals(alert.getZoneId())) {
                session.sendMessage(textMessage);
            }else{
                //este else lo puse para pruebas ok no me juzguen
                session.sendMessage(textMessage);
            }
        }
    }

    private Long extractZoneId(WebSocketSession session) {
        return 1L;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

    }
}
