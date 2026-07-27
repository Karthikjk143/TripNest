package com.tripnest.notification.service;

import com.tripnest.notification.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {
    NotificationDTO createNotification(NotificationDTO notificationDTO);
    void deleteNotification(Long id);
    NotificationDTO getNotificationById(Long id);
    List<NotificationDTO> getNotificationsByUser(Long userId);
    List<NotificationDTO> getUnreadNotifications(Long userId);
    NotificationDTO markAsRead(Long notificationId);
    void markAllAsRead(Long userId);
    Long getUnreadCount(Long userId);
}
