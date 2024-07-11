package vn.edu.fpt.SmartHealthC.domain.dto.response.FormQuestionResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.MonthlyRecordType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormMonthlyResponseDTO {
    private MonthlyRecordType type;
    private List<FormMonthlyQuestionDTO> formMonthlyQuestionDTOList;
}
