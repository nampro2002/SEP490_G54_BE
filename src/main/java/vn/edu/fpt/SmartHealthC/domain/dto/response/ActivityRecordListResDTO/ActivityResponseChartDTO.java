package vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTimeMeasure;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponseChartDTO {

    private int durationToday;
    private TypeActivity typeToDay;

    private List<ActivityResponse> activityResponseList = new ArrayList<>();


}
