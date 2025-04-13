package com.test.demo.notificationapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

//    private static final long EXPIRY_DURATION = 5 * 24 * 60 * 60;

    public void sendNotification(NotificationDTO dto) {
        String key = "notifications:" + dto.getUserId();

        List<NotificationDTO> notifications = (List<NotificationDTO>) redisTemplate.opsForValue().get(key);
        if (notifications == null) {
            notifications = new ArrayList<>();
        }

        dto.setCreatedAt(LocalDateTime.now());
        dto.setRead(false); // unread by default
        notifications.add(dto);

        redisTemplate.opsForValue().set(key, notifications, Duration.ofDays(5));
    }

    public List<NotificationDTO> getAllNotifications(String userId) {
        return getFromRedis(userId);
    }

    public List<NotificationDTO> getUnreadNotifications(String userId) {
        return getFromRedis(userId).stream()
                .filter(notification -> !notification.isRead())
                .collect(Collectors.toList());
    }

    public List<NotificationDTO> getReadNotifications(String userId) {
        return getFromRedis(userId).stream()
                .filter(NotificationDTO::isRead)
                .collect(Collectors.toList());
    }

    public void markAllAsRead(String userId) {
        List<NotificationDTO> notifications = getFromRedis(userId);
        notifications.forEach(n -> n.setRead(true));
        redisTemplate.opsForValue().set("notifications:" + userId, notifications, Duration.ofDays(5));
    }

    public void clearNotifications(String userId) {
        redisTemplate.delete("notifications:" + userId);
    }

    private List<NotificationDTO> getFromRedis(String userId) {
        String key = "notifications:" + userId;
        List<NotificationDTO> list = (List<NotificationDTO>) redisTemplate.opsForValue().get(key);
        return list != null ? list : new ArrayList<>();
    }
}
