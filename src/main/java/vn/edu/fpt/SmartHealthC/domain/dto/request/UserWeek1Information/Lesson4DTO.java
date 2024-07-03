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
public class Lesson4DTO {
    @NotNull(message = "missing score10")
    private Integer score10;
    @NotNull(message = "missing score20")
    private Integer score20;
    @NotNull(message = "missing score30")
    private Integer score30;
    @NotNull(message = "missing score40")
    private Integer score40;
    @NotNull(message = "missing score50")
    private Integer score50;
    @NotBlank(message = "missing recentValues")
    private String recentValues;
    @NotBlank(message = "missing influenceOnLife")
    private String influenceOnLife;
    @NotBlank(message = "missing newValues")
    private String newValues;
    @NotBlank(message = "missing reasonForChanging")
    private String reasonForChanging;


}
