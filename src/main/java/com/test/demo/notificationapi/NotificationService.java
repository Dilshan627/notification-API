package com.test.demo.notificationapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        notifications.add(dto);

        redisTemplate.opsForValue().set(key, notifications, Duration.ofDays(5));
    }

    public List<NotificationDTO> getUserNotifications(String userId) {
        String key = "notifications:" + userId;
        List<NotificationDTO> notifications = (List<NotificationDTO>) redisTemplate.opsForValue().get(key);
        return notifications != null ? notifications : new ArrayList<>();
    }

    public void clearNotifications(String userId) {
        String key = "notifications:" + userId;
        redisTemplate.delete(key);
    }
}
