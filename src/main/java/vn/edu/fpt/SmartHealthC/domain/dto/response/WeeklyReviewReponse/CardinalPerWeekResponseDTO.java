package vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardinalPerWeekResponseDTO {

    private int hba1cTotalRecord;

    private int hba1cSafeRecord;

    private int cholesterolTotalRecord;

    private int cholesterolSafeRecord;

    private int bloodSugarTotalRecord;

    private int bloodSugarSafeRecord;


}
