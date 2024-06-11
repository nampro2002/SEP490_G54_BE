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
public class BloodPressureRecordDTO {


    private int appUserId;

    private Date weekStart;

    private Date date;

    //Tâm thu
    private Float systole;

    //Tâm trương
    private Float diastole;


}
