package vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTimeMeasure;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordPerDay {
    private Date date;
    //Tâm thu
    private Float systole;

    //Tâm trương
    private Float diastole;
}
