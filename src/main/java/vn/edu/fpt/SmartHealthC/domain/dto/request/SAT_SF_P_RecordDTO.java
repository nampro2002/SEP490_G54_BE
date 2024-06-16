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
public class SAT_SF_P_RecordDTO {
    private Integer Id;
    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing monthStart")
    private Date monthStart;
    @NotNull(message = "missing overallPoint")
    private int overallPoint;
    @NotNull(message = "missing lifePursuit")
    private int lifePursuit;
    @NotNull(message = "missing planning")
    private int planning;
    @NotNull(message = "missing rightDecision")
    private int rightDecision;
    @NotNull(message = "missing priorityFocus")
    private int priorityFocus;
    @NotNull(message = "missing healthyEnvironment")
    private int healthyEnvironment;



}
