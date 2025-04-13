package com.test.demo.notificationapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public void sendNotification(@RequestBody NotificationDTO notification) {
        notificationService.sendNotification(notification);
    }

    @GetMapping("/get/{userId}")
    public List<NotificationDTO> getUserNotifications(@PathVariable String userId) {
        return notificationService.getUserNotifications(userId);
    }

    @DeleteMapping("/clear/{userId}")
    public void clearUserNotifications(@PathVariable String userId) {
        notificationService.clearNotifications(userId);
    }
}
