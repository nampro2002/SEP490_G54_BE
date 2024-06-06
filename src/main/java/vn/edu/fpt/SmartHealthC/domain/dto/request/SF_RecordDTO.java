package vn.edu.fpt.SmartHealthC.domain.dto.request;


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

    private int appUserId;

    private Date monthStart;

    private int positivity;

    private int activity;

    private int diet;

    private int medication;

    private int activityPlanning;

    private int activityHabit;

    private int healthyDiet;

    private int vegetablePrioritization;

    private int dietHabit;

    private int planCompliance;

    private int medicationHabit;



}
