package vn.edu.fpt.SmartHealthC.quartz.job;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.TopicNotificationRequest;
import vn.edu.fpt.SmartHealthC.serivce.notification.NotificationService;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MonthlyJob implements Job {
    @Autowired
    private NotificationService notificationService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Executing Monthly Job at: "+ new Date());
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
