package vn.edu.fpt.SmartHealthC.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeCardinalIndex;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTimeMeasure;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardinalRecordDTO {


    private int appUserId;

    private TypeTimeMeasure timeMeasure;

    private Date weekStart;

    private Date date;

    private Float cholesterol;
    private Float bloodSugar;
    private Float hba1c;


}
