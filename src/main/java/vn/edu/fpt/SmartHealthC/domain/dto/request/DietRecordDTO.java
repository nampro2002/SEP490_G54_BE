package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietRecordDTO {
    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing dishPerDay")
    private int  dishPerDay;
    @NotNull(message = "missing weekStart")
    private Date weekStart;
    @NotNull(message = "missing date")
    private Date date;

    private float  actualValue = 0;

}
