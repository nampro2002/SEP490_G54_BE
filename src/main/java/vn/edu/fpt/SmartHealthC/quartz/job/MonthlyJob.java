package vn.edu.fpt.SmartHealthC.quartz.job;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.checkerframework.checker.units.qual.A;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.TopicNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.response.AppUserDetailResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.quartz.quartzService.TriggerExecutionService;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.AccountService;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.WeeklyReviewService;
import vn.edu.fpt.SmartHealthC.serivce.notification.NotificationService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MonthlyJob implements Job {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private WeeklyReviewService weeklyReviewService;
    @Autowired
    private TriggerExecutionService triggerExecutionService;

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
        long daysBetween = ChronoUnit.DAYS.between(localDateTime, currentDate);
        if(daysBetween % 7 == 1 && daysBetween % 4 == 1){
            System.out.println("Send notification to user: " + appUser.getId());
        }
        // In kết quả
        System.out.println("Số ngày giữa " + currentDate + " và " + localDateTime + " là: " + daysBetween);
//        }
        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        TopicNotificationRequest topicNotificationRequest = TopicNotificationRequest.builder()
                .title("Monthly")
                .body("This is a monthly notification.")
                .imageUrl("http://example.com/image.png")
                .data(data)
                .topicName("monthly")
                .build();
        try {
            notificationService.sendPushNotificationToTopic(topicNotificationRequest);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
