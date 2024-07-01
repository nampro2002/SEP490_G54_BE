package vn.edu.fpt.SmartHealthC.domain.dto.request.UserWeek1Information;


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
public class Lesson3DTO {
    @NotNull(message = "missing planType")
    private TypeActivity planType;
    @NotNull(message = "missing weekStart")
    private Date weekStart;
    @NotNull(message = "missing planDuration")
    private Float planDuration;
    @NotNull(message = "missing schedule")
    private List<String> schedule;


}
