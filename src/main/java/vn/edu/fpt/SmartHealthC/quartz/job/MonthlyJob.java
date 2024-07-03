package vn.edu.fpt.SmartHealthC.quartz.job;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNotification;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.DeviceNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.NotificationSetting;
import vn.edu.fpt.SmartHealthC.domain.entity.RefreshToken;
import vn.edu.fpt.SmartHealthC.quartz.quartzService.TriggerExecutionService;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.NotificationSettingRepository;
import vn.edu.fpt.SmartHealthC.repository.RefreshTokenRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.NotificationService;
import vn.edu.fpt.SmartHealthC.serivce.WeeklyReviewService;
import vn.edu.fpt.SmartHealthC.serivce.Impl.NotificationServiceImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MonthlyJob implements Job {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private NotificationSettingRepository notificationSettingRepository;
    @Autowired
    private WeeklyReviewService weeklyReviewService;
    @Autowired
    private TriggerExecutionService triggerExecutionService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Executing Monthly Job at: " + new Date());
//        List<UserWeekStart> userWeekStarts = appUserService.getListAppUser().stream().map(user -> {
//            return UserWeekStart.builder()
//                    .userId(user.getId())
//                    .weekStart(weeklyReviewService.findSmallestWeekStart(user))
//                    .build();
//        }).toList();
//        Optional<AppUser> appUser = appUserService.findAppUserEntityById(1);
        AppUser appUser = triggerExecutionService.findAppUserById(1);
        UserWeekStart userWeekStart = UserWeekStart.builder()
                .userId(appUser.getId())
                .weekStart(weeklyReviewService.findSmallestWeekStart(appUser))
                .build();
//        for (UserWeekStart userWeekStart : userWeekStarts) {
        //Check if (date now + 1 -  userWeekStart.getWeekStart() ) % 7==0 & %4==1
        Instant instant = userWeekStart.getWeekStart().toInstant();

        // Convert Instant to LocalDateTime considering system default zone
        LocalDate localDateTime = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        LocalDate currentDate = LocalDate.now();
//        LocalDate currentDate = LocalDate.of(2024, 6, 8);
        long daysBetween = ChronoUnit.DAYS.between(localDateTime, currentDate);
        System.out.println("Days between Monthly : " + daysBetween);
        if(daysBetween % 7 == 0 && daysBetween % 4 == 1){
            NotificationSetting notificationSetting = notificationService.findByAccountIdAndType(appUser.getAccountId().getId(), TypeNotification.MONTHLY_REPORT_NOTIFICATION);
            System.out.println("Send notification to user: " + appUser.getId());
            if(notificationSetting.isStatus()){
                List<RefreshToken> refreshToken = refreshTokenRepository.findRecordByAccountId(appUser.getAccountId().getId());
                for (RefreshToken token : refreshToken){
                    HashMap<String, String> data = new HashMap<>();
                    data.put("key1", "value1");
                    DeviceNotificationRequest deviceNotificationRequest = DeviceNotificationRequest.builder()
                            .title("Monthly")
                            .body("This is a monthly notification.")
                            .imageUrl("http://example.com/image.png")
                            .data(data)
                            .deviceToken(token.getDeviceToken())
                            .build();
                    try {
                        notificationService.sendNotificationToDevice(deviceNotificationRequest);
                    } catch (FirebaseMessagingException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
//        }
    }
}
