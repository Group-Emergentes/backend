package com.aharon.config.websockets;

import com.aharon.sensors.service.SensorService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SensorService sensorService;

    public WebSocketConfig(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SensorWebSocketHandler(sensorService), "/ws/register-sensor-data")
                .setAllowedOrigins("*");
    }
}
