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
public class BloodPressureRecordDTO {

    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing weekStart")
    private Date weekStart;
    @NotNull(message = "missing date")
    private Date date;
    @NotNull(message = "missing systole")
    //Tâm thu
    private Float systole;
    @NotNull(message = "missing diastole")
    //Tâm trương
    private Float diastole;


}
