package vn.edu.fpt.SmartHealthC.domain.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeCardinalIndex;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTimeMeasure;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NMRDTO {
    private Integer appUserId;

    private Date weekStart;

    private Date date;
    private int timeMeasure;

    private TypeCardinalIndex typeCardinalIndex;

    private float value;
}
