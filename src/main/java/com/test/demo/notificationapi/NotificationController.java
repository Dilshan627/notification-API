package com.test.demo.notificationapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public String sendNotification(@RequestBody NotificationDTO dto) {
        notificationService.sendNotification(dto);
        return "Notification sent.";
    }

    @GetMapping("/{userId}/all")
    public List<NotificationDTO> getAll(@PathVariable String userId) {
        return notificationService.getAllNotifications(userId);
    }

    @GetMapping("/{userId}/unread")
    public List<NotificationDTO> getUnread(@PathVariable String userId) {
        return notificationService.getUnreadNotifications(userId);
    }

    @GetMapping("/{userId}/read")
    public List<NotificationDTO> getRead(@PathVariable String userId) {
        return notificationService.getReadNotifications(userId);
    }

    @PostMapping("/{userId}/mark-all-read")
    public String markAllAsRead(@PathVariable String userId) {
        notificationService.markAllAsRead(userId);
        return "All notifications marked as read.";
    }

    @DeleteMapping("/{userId}")
    public String clearNotifications(@PathVariable String userId) {
        notificationService.clearNotifications(userId);
        return "Notifications cleared.";
    }
}
