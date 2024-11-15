package com.aharon.notifications.repository;

import com.aharon.models.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.clientId = :clientId ORDER BY n.clientId DESC")
    List<Notification> findTop100ByClientIdOrderByTimestampDesc(@Param("clientId") Long clientId);

}
