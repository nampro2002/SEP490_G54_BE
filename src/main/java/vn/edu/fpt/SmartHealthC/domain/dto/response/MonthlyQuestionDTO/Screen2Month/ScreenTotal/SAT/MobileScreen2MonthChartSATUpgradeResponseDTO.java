package vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.Screen2Month.ScreenTotal.SAT;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MobileScreen2MonthChartSATUpgradeResponseDTO {

   private Integer screenNumber;
   private List<ScreenResponseDTO> list;

}
