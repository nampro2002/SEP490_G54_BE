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

    private int cholesterolTotalRecord;

    private int cholesterolSafeRecord;

    private int bloodSugarTotalRecord;

    private int bloodSugarSafeRecord;

    //BloodPressurePerWeekResponseDTO

    private int totalBloodPressureRecord;

    private int safeBloodPressureRecord;

    private int averageWeightRecordPerWeek;

    private int averageMentalRecordPerWeek;

    //ActivityPerWeekResponseDTO

    private int heavyActivity;

    private int mediumActivity;

    private int lightActivity;

    private int averageDietRecordPerWeek;
    //MedicinePerWeekResponseDTO

    private int medicineDateDone;

    private int medicineDateTotal;

    private int averageStepRecordPerWeek;

    private int totalPoint;

}
