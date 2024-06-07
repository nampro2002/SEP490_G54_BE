package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNumeral;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTimeMeasure;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumeralRecordDTO {

    private int id;

    private int appUserId;

    @Enumerated(EnumType.STRING)
    private TypeTimeMeasure timeMeasure;

    @Enumerated(EnumType.STRING)
    private TypeNumeral typeNumeral;

    private Date weekStart;

    private Date date;

    private float value;


}
