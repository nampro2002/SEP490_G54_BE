package vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.MonthlyStatisticResponseDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MobileScreenTotalChartResponseDTO {

   private MonthlyStatisticScreenTotalResponseDTO firstWeek;

   private List<MonthlyStatisticScreenTotalResponseDTO> chart3Month;


}
