package com.aharon.domain.serviceImpl;

import com.aharon.models.entities.Notification;
import com.aharon.notifications.repository.NotificationRepository;
import com.aharon.notifications.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public void createNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> getTop100ByClientIdOrderByTimestampDesc(Long clientId) {
        return notificationRepository.findTop100ByClientIdOrderByTimestampDesc(clientId);
    }
}
