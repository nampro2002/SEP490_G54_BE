package vn.edu.fpt.SmartHealthC.domain.dto.request;


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


    private int appUserId;

    private Date monthStart;

    private MonthlyRecordType monthlyRecordType;

    private int questionNumber;

    private String question;

    private int answer;



}
