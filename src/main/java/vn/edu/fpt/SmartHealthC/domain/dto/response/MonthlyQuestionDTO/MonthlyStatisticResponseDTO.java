package vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyStatisticResponseDTO {

    private SatResponseDTO satResponseDTO;
    private SFResponseDTO sfResponseDTO;
}