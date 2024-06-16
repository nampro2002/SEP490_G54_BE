package vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepRecordResListDTO {
    private Integer appUserId;

    private Date weekStart;

    private Float avgValue;

    List<RecordPerDay> recordPerDayList = new ArrayList<>();
}
