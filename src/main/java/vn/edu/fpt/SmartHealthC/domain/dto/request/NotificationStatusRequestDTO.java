package vn.edu.fpt.SmartHealthC.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNotification;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationStatusRequestDTO {
    private TypeNotification typeNotification;
    private boolean status;
}
