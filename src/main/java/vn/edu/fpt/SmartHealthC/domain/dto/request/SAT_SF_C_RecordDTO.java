package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.persistence.*;
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
public class SAT_SF_C_RecordDTO {
    @NotNull(message = "missing appUserId")
    private int appUserId;
    @NotNull(message = "missing monthStart")
    private Date monthStart;
    @NotNull(message = "missing overallPoint")
    private int overallPoint;
    @NotNull(message = "missing independence")
    private int independence;
    @NotNull(message = "missing optimistic")
    private int optimistic;
    @NotNull(message = "missing relationship")
    private int relationship;
    @NotNull(message = "missing sharedStory")
    private int sharedStory;



}
