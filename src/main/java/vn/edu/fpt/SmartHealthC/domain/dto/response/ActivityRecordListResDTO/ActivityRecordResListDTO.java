package vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityRecordResListDTO {
    private Integer appUserId;
    private Date weekStart;
    private Float avgValue;

    List<RecordPerDay> recordPerDayList = new ArrayList<>();

}
