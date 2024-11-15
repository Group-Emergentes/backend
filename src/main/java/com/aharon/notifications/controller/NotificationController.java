package com.aharon.notifications.controller;

import com.aharon.common.dto.ApiResponse;
import com.aharon.models.entities.Notification;
import com.aharon.notifications.service.NotificationService;
import com.aharon.sensors.dto.SensorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<Notification>>> getNotifications(
            @PathVariable("clientId") Long clientId){
        List<Notification> notifications = notificationService.getTop100ByClientIdOrderByTimestampDesc(clientId);

        ApiResponse<List<Notification>> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Notifications for " + clientId + " found");
        apiResponse.setData(notifications);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
