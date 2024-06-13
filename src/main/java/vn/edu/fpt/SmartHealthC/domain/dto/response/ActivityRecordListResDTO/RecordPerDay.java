package vn.edu.fpt.SmartHealthC.domain.dto.response.ActivityRecordListResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeActivity;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordPerDay {
    private TypeActivity activityType;

    private Float duration;

    private Date date;
}
