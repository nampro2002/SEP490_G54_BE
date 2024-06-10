package vn.edu.fpt.SmartHealthC.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyQuestionDTO {


    private int appUserId;

    private Date monthStart;

    private Boolean isSAT;

    private int questionNumber;

    private String question;

    private String answer;



}
