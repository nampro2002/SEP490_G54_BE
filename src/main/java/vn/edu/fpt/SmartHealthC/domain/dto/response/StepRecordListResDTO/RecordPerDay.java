package vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordPerDay {
    private Date date;
    private Float step;
}
