package vn.edu.fpt.SmartHealthC.quartz.job;

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
public class UserWeekStart {
    private AppUser appUser;
    private Date weekStart;
}
