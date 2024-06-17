package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.MonthlyRecordType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyQuestionDTO {

    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing monthStart")
    private Date monthStart;
    @NotNull(message = "missing monthlyRecordType")
    private MonthlyRecordType monthlyRecordType;
    @NotNull(message = "missing questionNumber")
    private Integer questionNumber;
    @NotBlank(message = "missing question")
    private String question;
    @NotNull(message = "missing answer")
    private Integer answer;



}
