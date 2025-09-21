package com.microshop.notification.repository;

import com.microshop.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Get all notifications of a user
    List<Notification> findByUserId(Long userId);

    // Get all notifications of a user by status (e.g., UNREAD, SENT)
    List<Notification> findByUserIdAndStatus(Long userId, String status);
}
