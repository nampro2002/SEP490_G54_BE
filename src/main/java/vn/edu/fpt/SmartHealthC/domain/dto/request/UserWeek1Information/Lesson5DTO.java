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
public class Lesson5DTO {
    @NotBlank(message = "missing currentEmotion")
    private String currentEmotion;
    @NotBlank(message = "missing whyIfRealistic")
    private String whyIfRealistic;
    @NotBlank(message = "missing whyIfNotBetterForLife")
    private String whyIfNotBetterForLife;




}
