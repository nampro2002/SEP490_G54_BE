package vn.edu.fpt.SmartHealthC.domain.dto.response.StepRecordListResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepResponseChartDTO {

    private int valueToday;

    private List<StepResponse> stepResponseList = new ArrayList<>();


}
