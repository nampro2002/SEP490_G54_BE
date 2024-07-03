package vn.edu.fpt.SmartHealthC.quartz.quartzService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class TriggerExecutionService {

    @Autowired
    private AppUserRepository appUserRepository;

    public boolean shouldExecuteMorningJob() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return dayOfWeek != DayOfWeek.MONDAY;
    }

    public boolean shouldExecuteEveningJob() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return dayOfWeek != DayOfWeek.SUNDAY;
    }

    public AppUser findAppUserById(int id) {
        return appUserRepository.findById(id).orElseThrow();
    }
}