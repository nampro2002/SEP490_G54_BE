package vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSubscriptionRequest {
    @NotBlank(message = "Device token is required")
    private String deviceToken;
    @NotBlank(message = "Topic name is required")
    private String topicName;
}
