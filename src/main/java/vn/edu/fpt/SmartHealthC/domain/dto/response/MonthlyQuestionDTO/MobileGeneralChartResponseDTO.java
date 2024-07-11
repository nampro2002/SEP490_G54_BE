package vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MobileGeneralChartResponseDTO {

   private MonthlyStatisticResponseDTO firstWeek;

   private List<MonthlyStatisticResponseDTO> chart3Month;


}
