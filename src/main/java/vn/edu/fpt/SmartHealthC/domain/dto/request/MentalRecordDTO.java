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
public class MentalRecordDTO {


    private int appUserId;

    private boolean status;

    private Date weekStart;

    private int mentalRuleId;

    private Date date;

}
