package vn.edu.fpt.SmartHealthC.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeekReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "appuser_id")
    @ToString.Exclude
    @JsonIgnore
    private AppUser appUserId;

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

    private int medicineRecordDone;

    private int medicineRecordTotal;

    private int averageStepRecordPerWeek;

    private int totalPoint;
}
