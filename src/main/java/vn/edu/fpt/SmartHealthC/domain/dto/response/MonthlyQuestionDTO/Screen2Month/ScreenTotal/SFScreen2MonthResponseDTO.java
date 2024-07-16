package vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.Screen2Month.ScreenTotal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SFScreen2MonthResponseDTO {

    private float sf_mental_modelPoint=0f;
    private float sf_activity_modelPoint=0f;
    private float sf_diet_modelPoint=0f;
    private float sf_medicine_modelPoint=0f;

    private float sf_mentalPoint=0f;

    private float sf_activity_planPoint=0f;
    private float sf_activity_habitPoint=0f;

    private float sf_diet_healthyPoint=0f;
    private float sf_diet_vegetablePoint=0f;
    private float sf_diet_habitPoint=0f;

    private float sf_medicine_followPlanPoint=0f;
    private float sf_medicine_habitPoint=0f;

}
