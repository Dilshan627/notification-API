package com.test.demo.notificationapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private String systemId;
    private String message;
    private String userId;
    private LocalDateTime createdAt;
    private boolean read;
}
