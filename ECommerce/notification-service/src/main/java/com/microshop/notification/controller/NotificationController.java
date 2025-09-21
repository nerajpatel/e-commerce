package com.microshop.notification.controller;

import com.microshop.notification.dto.NotificationRequest;
import com.microshop.notification.dto.NotificationResponse;
import com.microshop.notification.dto.PreferenceRequest;
import com.microshop.notification.dto.TemplateRequest;
import com.microshop.notification.model.Preference;
import com.microshop.notification.model.Template;
import com.microshop.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Send single notification
    @PostMapping("/send")
    public ResponseEntity<NotificationResponse> send(@Valid @RequestBody NotificationRequest req) {
        return ResponseEntity.ok(notificationService.sendNotification(req));
    }

    // Get user notifications
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    // Mark as read
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    // ---------------- Template CRUD ----------------
    @PostMapping("/templates")
    public ResponseEntity<Template> createTemplate(@Valid @RequestBody TemplateRequest req) {
        return ResponseEntity.ok(notificationService.createTemplate(req));
    }

    @GetMapping("/templates")
    public ResponseEntity<List<Template>> getTemplates() {
        return ResponseEntity.ok(notificationService.getAllTemplates());
    }

    @PutMapping("/templates/{id}")
    public ResponseEntity<Template> updateTemplate(@PathVariable Long id, @Valid @RequestBody TemplateRequest req) {
        return ResponseEntity.ok(notificationService.updateTemplate(id, req));
    }

    @DeleteMapping("/templates/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        notificationService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------- Preferences ----------------
    @GetMapping("/preferences/{userId}")
    public ResponseEntity<Preference> getPreference(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getPreference(userId));
    }

    @PutMapping("/preferences/{userId}")
    public ResponseEntity<Preference> updatePreference(@PathVariable Long userId,
                                                       @RequestBody PreferenceRequest req) {
        return ResponseEntity.ok(notificationService.updatePreference(userId, req));
    }
}
