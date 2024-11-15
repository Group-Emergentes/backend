package com.aharon.notifications.service;

import com.aharon.models.entities.Notification;

import java.util.List;

public interface NotificationService {
    void createNotification(Notification notification);
    List<Notification> getAllNotifications();
    List<Notification> getTop100ByClientIdOrderByTimestampDesc(Long zoneId);
}
