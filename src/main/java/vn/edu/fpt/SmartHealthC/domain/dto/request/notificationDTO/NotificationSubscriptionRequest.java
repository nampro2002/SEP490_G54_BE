package vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSubscriptionRequest {
    @NotBlank
    private String deviceToken;
    @NotBlank
    private String topicName;
}
