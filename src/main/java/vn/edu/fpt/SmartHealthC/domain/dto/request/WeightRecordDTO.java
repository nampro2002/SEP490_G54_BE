package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeightRecordDTO {
    @NotNull(message = "missing appUserId")
    private Integer appUserId;
    @NotNull(message = "missing weekStart")
    private Date weekStart;
    @NotNull(message = "missing date")
    private Date date;
    @NotNull(message = "missing weight")
    private Float weight;



}
