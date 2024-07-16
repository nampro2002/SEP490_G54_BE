package vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyStatisticScreenTotalResponseDTO {
//    private int month;
    private SatScreenTotalResponseDTO satResponseDTO;
    private SFScreenTotalResponseDTO sfResponseDTO;
}
