package vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO;

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
public class WeightResponseChartDTO {

    private Integer avgValue;
    private Float valueToday;
    private List<WeightResponse> weightResponseList = new ArrayList<>();


}
