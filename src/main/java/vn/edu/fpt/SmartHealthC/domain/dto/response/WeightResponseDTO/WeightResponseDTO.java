package vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeightResponseDTO {

    private Integer appUserId;

    private Date weekStart;

    private String avgValue;

    List<RecordPerDay> recordPerDayList = new ArrayList<>();


}
