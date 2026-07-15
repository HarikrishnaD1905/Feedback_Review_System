package com.examly.springapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Feedback;

/**
 * FR8: Automated Admin Notifications.
 * Currently operates in log-only mode. To enable real SMTP email:
 * 1. Add spring-boot-starter-mail to pom.xml
 * 2. Configure SMTP properties in application.properties
 * 3. Inject JavaMailSender and uncomment the send logic
 */
@Service
public class EmailNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

    @Value("${app.admin.notification-email:admin@feedbacksystem.com}")
    private String adminEmail;

    @Async
    public void notifyAdminNewFeedback(Feedback feedback) {
        String subject = "New Feedback Submitted - FMS";
        String contentSnippet = feedback.getFeedbackContent();
        if (contentSnippet.length() > 200) {
            contentSnippet = contentSnippet.substring(0, 200) + "...";
        }

        String body = String.format(
                "A new feedback has been submitted.%n%n" +
                "Submitter Name: %s%n" +
                "Submitter Email: %s%n" +
                "Rating: %d%n" +
                "Content: %s%n%n" +
                "Please log in to the admin dashboard to review.",
                feedback.getSubmitterName() != null ? feedback.getSubmitterName() : "Anonymous",
                feedback.getSubmitterEmail(),
                feedback.getRating(),
                contentSnippet
        );

        // Log-only mode: logs the email notification to console
        logger.info("=== EMAIL NOTIFICATION ===");
        logger.info("To: {}", adminEmail);
        logger.info("Subject: {}", subject);
        logger.info("Body:\n{}", body);
        logger.info("=== END EMAIL NOTIFICATION ===");
    }
}
