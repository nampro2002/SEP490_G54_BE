package vn.edu.fpt.SmartHealthC.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNotification;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSettingResponseDTO {
    private TypeNotification typeNotification;
    private boolean status;
}
