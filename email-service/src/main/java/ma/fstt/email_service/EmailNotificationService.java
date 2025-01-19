package ma.fstt.email_service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
public class EmailNotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendCreditResponseEmail(CreditResponse creditResponse) {
        try {
            // Build email content
            String subject = "Credit Response Notification";
            String message = String.format(
                    "<html><body>" +
                            "<p>Dear <strong>%s</strong>,</p>" +
                            "<p>Your credit request (ID: <strong>%d</strong>) has been <strong>%s</strong>.</p>" +
                            "<p>Thank you for choosing our services.</p>" +
                            "</body></html>",
                    creditResponse.getName(),
                    creditResponse.getIdCreditDemand(),
                    creditResponse.getCreditResponse()
            );

            // Create and send the email
            MimeMessage email = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(email, true);
            helper.setTo(creditResponse.getEmail());
            helper.setSubject(subject);
            helper.setText(message, true); // Set the second parameter to `true` for HTML content

            mailSender.send(email);
        } catch (MessagingException e) {
            // Log the exception or handle it appropriately
            System.err.println("Failed to send email: " + e.getMessage());
            throw new RuntimeException("Failed to send email", e); // Re-throw as a runtime exception
        }
    }
}