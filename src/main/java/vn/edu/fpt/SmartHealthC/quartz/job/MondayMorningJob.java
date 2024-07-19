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
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.TopicNotificationRequest;
import vn.edu.fpt.SmartHealthC.serivce.Impl.NotificationServiceImpl;
import vn.edu.fpt.SmartHealthC.serivce.NotificationService;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
@Slf4j
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MondayMorningJob  implements Job {
    @Autowired
    private NotificationService notificationService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Executing Monday Morning Job at: " + new Date());
        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        TopicNotificationRequest topicNotificationRequest = TopicNotificationRequest.builder()
                .title("quản lý kế hoạch")
                .body("quản lý dữ liệu kế hoạch thực hiện")
                .imageUrl("http://example.com/image.png")
                .data(data)
                .topicName("mondayam")
                .build();
        try {
            notificationService.sendPushNotificationToTopic(topicNotificationRequest);
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