package vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordPerDay {
    private Date date;
    //Tâm thu
    private String systole;

    //Tâm trương
    private String diastole;
}
