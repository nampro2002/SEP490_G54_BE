package vn.edu.fpt.SmartHealthC.quartz.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWeekStart {
    private Integer userId;
    private Date weekStart;
}
