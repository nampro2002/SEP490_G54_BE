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
public class StepResponse {

    private int valuePercent;
    private Date date;

}
