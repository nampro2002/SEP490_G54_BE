package vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeekCheckPlanResponseDTO {

    private Boolean mentalPlan;

    private Boolean activityPlan;

    private Boolean dietPlan;

    private Boolean medicinePLan;

    private Boolean stepPlan;




}
