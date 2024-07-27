package vn.edu.fpt.SmartHealthC.quartz.job;


import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeTopic;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.TopicNotificationRequest;
import vn.edu.fpt.SmartHealthC.quartz.quartzService.TriggerExecutionService;
import vn.edu.fpt.SmartHealthC.serivce.Impl.NotificationServiceImpl;
import vn.edu.fpt.SmartHealthC.serivce.NotificationService;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
@Slf4j
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DailyMorningJob implements Job {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private TriggerExecutionService triggerExecutionService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (triggerExecutionService.shouldExecuteMorningJob()) {
            System.out.println("Executing Daily Evening Job at: " + new Date());

            HashMap<String, String> data = new HashMap<>();
            data.put("key1", "value1");
            TopicNotificationRequest topicNotificationRequestEn = TopicNotificationRequest.builder()
                    .title("Smart Healthing C")
                    .body("Check out what to do today and do it")
                    .imageUrl("")
                    .data(data)
                    .topicName(TypeTopic.DAILY_EN.getTopicName())
                    .build();
            TopicNotificationRequest topicNotificationRequestKr = TopicNotificationRequest.builder()
                    .title("스마트 헬싱 C")
                    .body("오늘 할 일을 확인하고 실천하세요")
                    .imageUrl("")
                    .data(data)
                    .topicName(TypeTopic.DAILY_KR.getTopicName())
                    .build();
            try {
                notificationService.sendPushNotificationToTopic(topicNotificationRequestEn);
                notificationService.sendPushNotificationToTopic(topicNotificationRequestKr);
            } catch (FirebaseMessagingException e) {
//                throw new RuntimeException(e);
                log.error("RuntimeException", e);
            } catch (ExecutionException e) {
                log.error("ExecutionException", e);
//                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                log.error("InterruptedException", e);
//                throw new RuntimeException(e);
            }
        }
    }
}