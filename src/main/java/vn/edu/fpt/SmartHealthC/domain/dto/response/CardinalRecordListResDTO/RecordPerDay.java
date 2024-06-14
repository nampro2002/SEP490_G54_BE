package vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO;

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
    private TypeTimeMeasure timeMeasure;
    private String Cholesterol;
    private String HBA1C;
    private String BloodSugar;
}
