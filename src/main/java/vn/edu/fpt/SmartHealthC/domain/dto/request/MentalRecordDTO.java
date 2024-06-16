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
public class MentalRecordDTO {

    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing status")
    private boolean status;
    @NotNull(message = "missing weekStart")
    private Date weekStart;
    @NotNull(message = "missing mentalRuleId")
    private int mentalRuleId;
    @NotNull(message = "missing date")
    private Date date;

}
