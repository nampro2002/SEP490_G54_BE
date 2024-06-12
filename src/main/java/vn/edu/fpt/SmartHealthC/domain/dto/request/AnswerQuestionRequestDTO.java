package vn.edu.fpt.SmartHealthC.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerQuestionRequestDTO {
    private Integer webUserId;
    private String answer;
}
