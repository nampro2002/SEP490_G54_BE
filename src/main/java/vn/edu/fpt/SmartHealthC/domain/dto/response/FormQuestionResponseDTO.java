package vn.edu.fpt.SmartHealthC.domain.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeQuestion;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormQuestionResponseDTO {
    private Integer id;
    private String question;
    private TypeQuestion type;
    private int questionNumber;
}


