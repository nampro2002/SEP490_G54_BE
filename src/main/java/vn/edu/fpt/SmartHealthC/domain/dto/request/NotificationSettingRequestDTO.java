package vn.edu.fpt.SmartHealthC.domain.dto.request;

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
    private List<TypeNotification> typeNotificationList;
    private boolean status;
    private String deviceToken;
}
