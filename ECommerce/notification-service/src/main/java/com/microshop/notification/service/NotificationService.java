package com.microshop.notification.service;

import com.microshop.notification.dto.NotificationRequest;
import com.microshop.notification.dto.NotificationResponse;
import com.microshop.notification.dto.PreferenceRequest;
import com.microshop.notification.dto.TemplateRequest;
import com.microshop.notification.model.Notification;
import com.microshop.notification.model.Preference;
import com.microshop.notification.model.Template;
import com.microshop.notification.repository.NotificationRepository;
import com.microshop.notification.repository.PreferenceRepository;
import com.microshop.notification.repository.TemplateRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepo;
    private final TemplateRepository templateRepo;
    private final PreferenceRepository preferenceRepo;

    public NotificationService(NotificationRepository notificationRepo, TemplateRepository templateRepo, PreferenceRepository preferenceRepo) {
        this.notificationRepo = notificationRepo;
        this.templateRepo = templateRepo;
        this.preferenceRepo = preferenceRepo;
    }

    // Send single notification
    public NotificationResponse sendNotification(NotificationRequest req) {
        Notification notification = Notification.builder()
                .userId(req.getUserId())
                .channel(req.getChannel())
                .subject(req.getSubject())
                .message(req.getMessage())
                .status(Notification.Status.SENT) // assume success (could integrate with email/SMS)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
        notificationRepo.save(notification);
        return toResponse(notification);
    }

    // Get notifications by user
    public List<NotificationResponse> getUserNotifications(Long userId) {
        return notificationRepo.findByUserId(userId)
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Mark notification as read
    @Transactional
    public void markAsRead(Long id) {
        Notification notification = notificationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
    }

    // -------------------- Template CRUD --------------------
    public Template createTemplate(TemplateRequest req) {
        Template template = Template.builder()
                .name(req.getName())
                .subject(req.getSubject())
                .body(req.getBody())
                .build();
        return templateRepo.save(template);
    }

    public List<Template> getAllTemplates() {
        return templateRepo.findAll();
    }

    public Template updateTemplate(Long id, TemplateRequest req) {
        Template template = templateRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));
        template.setName(req.getName());
        template.setSubject(req.getSubject());
        template.setBody(req.getBody());
        return templateRepo.save(template);
    }

    public void deleteTemplate(Long id) {
        templateRepo.deleteById(id);
    }

    // -------------------- Preferences --------------------
    public Preference updatePreference(Long userId, PreferenceRequest req) {
        Preference pref = preferenceRepo.findById(userId)
                .orElse(Preference.builder().userId(userId).emailEnabled(true).smsEnabled(true).pushEnabled(true).inAppEnabled(true).build());
        pref.setEmailEnabled(req.isEmailEnabled());
        pref.setSmsEnabled(req.isSmsEnabled());
        pref.setPushEnabled(req.isPushEnabled());
        pref.setInAppEnabled(req.isInAppEnabled());
        return preferenceRepo.save(pref);
    }

    public Preference getPreference(Long userId) {
        return preferenceRepo.findById(userId)
                .orElse(Preference.builder().userId(userId).emailEnabled(true).smsEnabled(true).pushEnabled(true).inAppEnabled(true).build());
    }

    // Utility
    private NotificationResponse toResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .userId(n.getUserId())
                .channel(n.getChannel())
                .subject(n.getSubject())
                .message(n.getMessage())
                .status(n.getStatus().name())
                .isRead(n.isRead())
                .build();
    }
}
