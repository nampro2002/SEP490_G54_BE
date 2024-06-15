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
public class SAT_SF_I_RecordDTO {

    private int appUserId;

    private Date monthStart;

    private int overallPoint;

    private int selfControl;

    private int stressFacing;

    private int consistency;

    private int energyConservation;

    private int motivation;

    private int revision;



}
