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
public class SF_RecordDTO {
    private Integer Id;
    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing monthStart")
    private Date monthStart;
    @NotNull(message = "missing positivity")
    private int positivity;
    @NotNull(message = "missing activity")
    private int activity;
    @NotNull(message = "missing diet")
    private int diet;
    @NotNull(message = "missing medication")
    private int medication;
    @NotNull(message = "missing activityPlanning")
    private int activityPlanning;
    @NotNull(message = "missing activityHabit")
    private int activityHabit;
    @NotNull(message = "missing healthyDiet")
    private int healthyDiet;
    @NotNull(message = "missing vegetablePrioritization")
    private int vegetablePrioritization;
    @NotNull(message = "missing dietHabit")
    private int dietHabit;
    @NotNull(message = "missing planCompliance")
    private int planCompliance;
    @NotNull(message = "missing medicationHabit")
    private int medicationHabit;



}
