package vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.Screen2Month.ScreenTotal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MobileScreen2MonthChartSFResponseDTO {
//   private int month;
   private SFScreen2MonthResponseDTO sfResponseDTO;
}
