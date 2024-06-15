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
public class SAT_SF_C_RecordDTO {

    private int appUserId;

    private Date monthStart;

    private int overallPoint;

    private int independence;

    private int optimistic;

    private int relationship;

    private int sharedStory;



}
