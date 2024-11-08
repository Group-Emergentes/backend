package com.aharon.notifications.service;

import com.aharon.models.entities.Notification;

import java.util.List;

public interface NotificationService {
    Notification createNotification(Notification notification);
    List<Notification> getAllNotifications();
}
