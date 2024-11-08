package com.aharon.config.websockets;

import com.aharon.notifications.service.NotificationService;
import com.aharon.sensors.service.SensorService;
import com.aharon.zones.service.ZoneService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SensorService sensorService;
    private final ZoneService zoneService;
    private final AlertWebSocketHandler alertWebSocketHandler;
    private final NotificationService notificationService;

    public WebSocketConfig(
            SensorService sensorService,
            ZoneService zoneService,
            AlertWebSocketHandler alertWebSocketHandler,
            NotificationService notificationService) {
        this.sensorService = sensorService;
        this.zoneService = zoneService;
        this.alertWebSocketHandler = alertWebSocketHandler;
        this.notificationService = notificationService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SensorWebSocketHandler(
                                sensorService, zoneService, alertWebSocketHandler, notificationService),
                        "/ws/register-sensor-data/{zoneId}")
                .setAllowedOrigins("*");

        registry.addHandler(alertWebSocketHandler, "/ws/alert-system")
                .setAllowedOrigins("*");
    }
}
