package vn.edu.fpt.SmartHealthC.domain.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.MonthlyRecordType;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeQuestion;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormQuestionRequestDTO {
    @NotBlank(message = "missing question")
    private String question;
    @NotBlank(message = "missing questionEn")
    private String questionEn;
    @NotNull(message = "missing type")
    private MonthlyRecordType type;
    @NotNull(message = "missing questionNumber")
    private Integer questionNumber;
}
