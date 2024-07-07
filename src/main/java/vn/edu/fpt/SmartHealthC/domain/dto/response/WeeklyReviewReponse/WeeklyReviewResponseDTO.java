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
public class WeeklyReviewResponseDTO {


    private Date weekStart;


    //CardinalPerWeekResponseDTO
    private int hba1cTotalRecord;

    private int hba1cSafeRecord;

    private int hba1cPoint;

    private int cholesterolTotalRecord;

    private int cholesterolSafeRecord;

    private int cholesterolPoint;

    private int bloodSugarTotalRecord;

    private int bloodSugarSafeRecord;

    private int bloodSugarPoint;

    //BloodPressurePerWeekResponseDTO

    private int totalBloodPressureRecord;

    private int safeBloodPressureRecord;

    private int bloodPressurePoint;

    private int averageWeightRecordPerWeek;

    private int weightPoint;

    private int averageMentalRecordPerWeek;

    private int mentalPoint;

    //ActivityPerWeekResponseDTO

    private int heavyActivity;

    private int mediumActivity;

    private int lightActivity;

    private int activityPoint;

    private int averageDietRecordPerWeek;

    private int dietPoint;
    //MedicinePerWeekResponseDTO

    private int medicineDateDone;

    private int medicineDateTotal;

    private int medicinePoint;

    private int averageStepRecordPerWeek;

    private int stepPoint;

    private int totalPoint;

}
