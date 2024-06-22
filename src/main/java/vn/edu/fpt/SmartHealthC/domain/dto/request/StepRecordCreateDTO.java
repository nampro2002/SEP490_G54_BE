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
public class StepRecordCreateDTO {
    @NotNull(message = "missing plannedStepPerDay")
    private int  plannedStepPerDay;
    @NotNull(message = "missing weekStart")
    private Date weekStart;


}
