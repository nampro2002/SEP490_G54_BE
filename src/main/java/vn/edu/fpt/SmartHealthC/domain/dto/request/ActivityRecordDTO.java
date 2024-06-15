package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityRecordDTO {
    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing planType")
    private TypeActivity planType;
    private TypeActivity actualType;
    @NotNull(message = "missing weekStart")
    private Date weekStart;
    @NotNull(message = "missing planDuration")
    private Float planDuration;

    private Float actualDuration = (float) 0;
    @NotNull(message = "missing date")
    private Date date;


}
