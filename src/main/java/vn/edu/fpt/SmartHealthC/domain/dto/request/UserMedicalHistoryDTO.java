package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMedicalHistoryDTO {
    @NotNull(message = "missing conditionId")
    private int  conditionId;

}
