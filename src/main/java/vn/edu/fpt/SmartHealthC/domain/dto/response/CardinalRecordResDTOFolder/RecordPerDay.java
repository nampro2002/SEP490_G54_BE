package vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordResDTOFolder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTimeMeasure;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordPerDay {
    private Date date;
    private TypeTimeMeasure timeMeasure;
    private Float Cholesterol;
    private Float HBA1C;
    private Float BloodSugar;
}
