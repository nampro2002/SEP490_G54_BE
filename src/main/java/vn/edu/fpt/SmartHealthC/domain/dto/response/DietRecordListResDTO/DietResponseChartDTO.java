package vn.edu.fpt.SmartHealthC.domain.dto.response.DietRecordListResDTO;

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
public class DietResponseChartDTO {

    private int avgValue;
    private List<DietResponse> dietResponseList = new ArrayList<>();


}
