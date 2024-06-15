package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StepRecordDTO {

    private int id;
    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing plannedStepPerDay")
    private int  plannedStepPerDay;
    @NotNull(message = "missing weekStart")
    private Date weekStart;
    @NotNull(message = "missing date")
    private Date date;
    private float  actualValue;


}
