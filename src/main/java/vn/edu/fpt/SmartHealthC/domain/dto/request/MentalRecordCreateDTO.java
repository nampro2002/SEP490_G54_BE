package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentalRecordCreateDTO {

    @NotNull(message = "missing weekStart")
    private Date weekStart;
    @NotNull(message = "missing mentalRuleId")
    private List<Integer> mentalRuleId;


}