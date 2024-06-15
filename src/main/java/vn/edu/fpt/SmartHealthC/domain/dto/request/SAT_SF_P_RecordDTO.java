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
public class SAT_SF_P_RecordDTO {
    private Integer Id;

    private int appUserId;

    private Date monthStart;

    private int overallPoint;

    private int lifePursuit;

    private int planning;

    private int rightDecision;

    private int priorityFocus;

    private int healthyEnvironment;



}
