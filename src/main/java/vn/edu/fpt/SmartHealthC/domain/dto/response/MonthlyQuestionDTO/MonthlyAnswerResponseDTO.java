package vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.MonthlyRecordType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyAnswerResponseDTO {

    private Integer questionNumber;
    private String question;
    private MonthlyRecordType type;
    private Integer answer;
}
