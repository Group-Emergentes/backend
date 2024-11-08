package com.aharon.config.websockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class AlertWebSocketHandler extends TextWebSocketHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final Map<WebSocketSession, Long> sessionZoneMap = new ConcurrentHashMap<>();

    public AlertWebSocketHandler() {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        Long zoneId = extractZoneId(session);
        sessionZoneMap.put(session, zoneId); // Asociar sesión con zona
        sendCurrentAlerts(session, zoneId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        sessionZoneMap.remove(session);
    }

    private void sendCurrentAlerts(WebSocketSession session, Long zoneId) throws Exception {
        ObjectNode alerts = SensorWebSocketHandler.getAlertCache(zoneId);
        if (alerts != null && !alerts.isEmpty()) {
            TextMessage alertMessage = new TextMessage(alerts.toString());
            session.sendMessage(alertMessage);
        }
    }

    public void broadcastAlerts(ObjectNode alerts, Long zoneId) throws Exception {
        if (alerts != null && !alerts.isEmpty()) {
            TextMessage alertMessage = new TextMessage(alerts.toString());
            for (WebSocketSession session : sessions) {
                if (session.isOpen() && sessionZoneMap.get(session).equals(zoneId)) {
                    session.sendMessage(alertMessage);
                }
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
