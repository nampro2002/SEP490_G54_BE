package vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodPressurePerWeekResponseDTO {

    private int totalRecord;

    private int safeRecord;



}
