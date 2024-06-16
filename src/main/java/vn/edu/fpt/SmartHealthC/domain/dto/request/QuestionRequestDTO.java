package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionRequestDTO {

    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing typeUserQuestion")
    private TypeUserQuestion typeUserQuestion;
    @NotBlank(message = "missing title")
    private String  title;
    @NotBlank(message = "missing body")
    private String body;


}
