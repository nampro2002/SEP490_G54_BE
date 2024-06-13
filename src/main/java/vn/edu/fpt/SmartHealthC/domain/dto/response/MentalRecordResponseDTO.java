package vn.edu.fpt.SmartHealthC.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentalRecordResponseDTO {
    private int appUserId;

    private boolean status;

    private Date weekStart;

    private int mentalRuleId;

    private Date date;


}
