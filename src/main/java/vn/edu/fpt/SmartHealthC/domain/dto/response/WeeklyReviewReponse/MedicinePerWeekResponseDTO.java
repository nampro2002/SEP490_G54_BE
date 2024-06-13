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
public class MedicinePerWeekResponseDTO {

    private int medicineRecordDone;

    private int medicineRecordTotal;


}
