package vn.edu.fpt.SmartHealthC.domain.dto.request;


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
public class ActivityRecordUpdateDTO {
    @NotNull(message = "missing actualType")
    private TypeActivity actualType;
    @NotNull(message = "missing actualDuration")
    private Float actualDuration;
    @NotNull(message = "missing date")
    private Date date;

}
