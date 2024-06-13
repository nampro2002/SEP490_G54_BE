package vn.edu.fpt.SmartHealthC.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietRecordDTO {

    private int appUserId;

    private int  dishPerDay;

    private Date weekStart;

    private Date date;
    private float  actualValue;

}
