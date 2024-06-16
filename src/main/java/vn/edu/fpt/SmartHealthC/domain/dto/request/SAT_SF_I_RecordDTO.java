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
public class SAT_SF_I_RecordDTO {
    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing monthStart")
    private Date monthStart;
    @NotNull(message = "missing overallPoint")
    private int overallPoint;
    @NotNull(message = "missing selfControl")
    private int selfControl;
    @NotNull(message = "missing stressFacing")
    private int stressFacing;
    @NotNull(message = "missing consistency")
    private int consistency;
    @NotNull(message = "missing energyConservation")
    private int energyConservation;
    @NotNull(message = "missing motivation")
    private int motivation;
    @NotNull(message = "missing revision")
    private int revision;



}
