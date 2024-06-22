package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StepRecordUpdateDTO {
    @NotNull(message = "missing date")
    private Date date;
    @NotNull(message = "missing actualValue")
    private Float  actualValue;


}
