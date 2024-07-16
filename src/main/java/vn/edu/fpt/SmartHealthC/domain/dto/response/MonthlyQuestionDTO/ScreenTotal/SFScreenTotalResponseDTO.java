package vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SFScreenTotalResponseDTO {

    private float sf_mental_modelPoint=0f;
    private float sf_activity_modelPoint=0f;
    private float sf_diet_modelPoint=0f;
    private float sf_medicine_modelPoint=0f;



}
