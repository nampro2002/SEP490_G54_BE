package vn.edu.fpt.SmartHealthC.domain.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponseDTO {
    private Integer id;

    private String appUserName;

    private String webUserName;

    private String title;

    private String body;

    private String answer;

    private Date questionDate;
    private Date answerDate;
}
