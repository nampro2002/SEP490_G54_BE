package vn.edu.fpt.SmartHealthC.domain.dto.response.WeeklyReviewReponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyReviewWebResponseDTO {
    private Date weekStart;
    private CardinalPerWeekResponseDTO cardinalPerWeek;
    private BloodPressurePerWeekResponseDTO bloodPressurePerWeek;
    private int averageWeightRecordPerWeek;
    private int averageMentalRecordPerWeek;
    private ActivityPerWeekWebResponseDTO activityRecordPerWeek;
    private int averageDietRecordPerWeek;
    private MedicinePerWeekResponseDTO medicineRecordPerWeek;
    private int averageStepRecordPerWeek;

}
