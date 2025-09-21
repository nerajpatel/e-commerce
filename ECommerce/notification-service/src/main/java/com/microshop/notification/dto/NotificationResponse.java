package com.microshop.notification.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificationResponse {
    private Long id;
    private Long userId;
    private String channel;
    private String subject;
    private String message;
    private String status;
    private boolean isRead;
}
