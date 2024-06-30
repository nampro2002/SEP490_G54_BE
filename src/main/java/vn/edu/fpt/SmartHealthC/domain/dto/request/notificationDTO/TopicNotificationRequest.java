package vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Builder
public class TopicNotificationRequest{
    @NotBlank
    private String topicName;
    @NotBlank
    private String title;
    @NotBlank
    private String body;
    private String imageUrl;
    private Map<String, String> data = new HashMap<>();
}
