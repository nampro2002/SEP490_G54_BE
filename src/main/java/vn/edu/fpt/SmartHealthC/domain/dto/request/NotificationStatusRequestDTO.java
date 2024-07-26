package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Type notification is required")
    private TypeNotification typeNotification;
    @NotNull(message = "Status is required")
    private boolean status;
}
