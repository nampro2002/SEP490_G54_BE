package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.persistence.*;
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
public class StepRecordDTO {

    private int id;

    private int appUserId;

    private int  plannedStepPerDay;

    private Date weekStart;

    private Date date;

    private float  actualValue;


}
