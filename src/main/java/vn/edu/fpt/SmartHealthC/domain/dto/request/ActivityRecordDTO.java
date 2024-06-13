package vn.edu.fpt.SmartHealthC.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityRecordDTO {

    private int appUserId;

    private TypeActivity planType;
    private TypeActivity actualType;

    private Date weekStart;

    private Float planDuration;

    private Float actualDuration;

    private Date date;



}
