package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignRequestDTO {
    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing webUserId")
    private int webUserId;
}
