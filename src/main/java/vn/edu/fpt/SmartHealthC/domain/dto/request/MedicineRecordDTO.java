package vn.edu.fpt.SmartHealthC.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineRecordDTO {


    private int appUserId;

    private Date weekStart;

    private Float hour;

    private Date date;

    private Boolean status;

}
