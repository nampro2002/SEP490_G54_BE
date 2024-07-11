package vn.edu.fpt.SmartHealthC.domain.dto.response.FormQuestionResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormMonthlyQuestionDTO {
    private int questionNumber;
    private String question;
}
