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
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class AlertWebSocketHandler extends TextWebSocketHandler {

    private final NotificationService notificationService;
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public AlertWebSocketHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        sendCurrentAlerts(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    private void sendCurrentAlerts(WebSocketSession session) throws Exception {

    }

    public void broadcastAlerts(Notification alert) throws Exception {
        ObjectNode alertMessage = objectMapper.createObjectNode();
        alertMessage.put("sensorId", alert.getSensorId());
        alertMessage.put("message", alert.getMessage());
        TextMessage textMessage = new TextMessage(alertMessage.toString());

        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(textMessage);
            }
        }
    }
}
