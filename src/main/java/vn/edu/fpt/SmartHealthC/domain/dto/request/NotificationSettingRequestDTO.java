package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNotification;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSettingRequestDTO {
    @NotBlank(message = "Device token is required")
    private String deviceToken;
    @NotNull(message = "Status is required")
    private List<NotificationStatusRequestDTO> notificationStatusList;
}
