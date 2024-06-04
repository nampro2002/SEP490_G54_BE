package vn.edu.fpt.SmartHealthC.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {

    private int id;

    private int appUserId;

    private int webUserId;

    private String  title;

    private String body;

    private String answer;


}
