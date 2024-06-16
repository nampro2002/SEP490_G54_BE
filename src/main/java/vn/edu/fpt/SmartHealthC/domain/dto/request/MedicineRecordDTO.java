package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineRecordDTO {

    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing weekStart")
    private Date weekStart;
    @NotNull(message = "missing medicineTypeId")
    private int medicineTypeId;
    @NotNull(message = "missing date")
    private Date date;
    @NotNull(message = "missing status")
    private Boolean status;

}
