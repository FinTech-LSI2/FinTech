package ma.fstt.email_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class CreditResponseController {

    @Autowired
    private EmailNotificationService emailNotificationService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendNotification(@RequestBody CreditResponse creditResponse) {
        emailNotificationService.sendCreditResponseEmail(creditResponse);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Notification email sent successfully.");
        return ResponseEntity.ok(response);
    }


}
