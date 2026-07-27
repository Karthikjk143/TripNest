package com.tripnest.notification.dto;

import com.tripnest.notification.entity.NotificationType;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private Long userId;
    private NotificationType type;
    private String title;
    private String message;
    private Boolean isRead;
    private String relatedEntityId;
    private LocalDateTime createdAt;
}
