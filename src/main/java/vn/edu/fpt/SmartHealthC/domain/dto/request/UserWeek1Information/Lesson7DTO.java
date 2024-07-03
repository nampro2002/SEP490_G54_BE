package vn.edu.fpt.SmartHealthC.domain.dto.request.UserWeek1Information;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson7DTO {
    @NotBlank(message = "missing whatIsHealth")
    private String whatIsHealth;
    @NotBlank(message = "missing activityCommitment")
    private String activityCommitment;
    @NotBlank(message = "missing dietCommitment")
    private String dietCommitment;
    @NotBlank(message = "missing mentalCommitment")
    private String mentalCommitment;
    @NotBlank(message = "missing medicineCommitment")
    private String medicineCommitment;
    @NotBlank(message = "missing roadBlock")
    private String roadBlock;
    @NotBlank(message = "missing solution")
    private String solution;
    @NotBlank(message = "missing commitment")
    private String commitment;


}
