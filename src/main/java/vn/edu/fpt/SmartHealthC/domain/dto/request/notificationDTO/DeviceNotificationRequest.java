package vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Builder
public class DeviceNotificationRequest {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Body is required")
    private String body;
    private String imageUrl;
    private Map<String, String> data = new HashMap<>();
    @NotBlank(message = "Device token is required")
    private String deviceToken;
}
