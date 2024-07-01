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
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
import vn.edu.fpt.SmartHealthC.serivce.MedicalAppointmentService;
import vn.edu.fpt.SmartHealthC.serivce.notification.NotificationService;

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
public class MedicalAppointmentJob implements Job {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private MedicalAppointmentService medicalAppointmentService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<MedicalAppointment> medicalAppointments = medicalAppointmentService.getMedicalAppointmentConfirm();
        for (MedicalAppointment item: medicalAppointments){
            Instant instant =  item.getDate().toInstant();

            // Convert Instant to LocalDateTime considering system default zone
            LocalDate localDateTime = LocalDate.ofInstant(instant, ZoneId.systemDefault());
            LocalDate currentDate = LocalDate.now();
            long daysBetween = ChronoUnit.DAYS.between(localDateTime, currentDate);
            //if daysBetween = 1, 3, 7
            if (daysBetween == 1 || daysBetween == 3 || daysBetween == 7){
                HashMap<String, String> data = new HashMap<>();
                data.put("key1", "value1");
                TopicNotificationRequest topicNotificationRequest = TopicNotificationRequest.builder()
                        .title("Medical Appointment")
                        .body("You have a medical appointment in " + daysBetween + " days.")
                        .imageUrl("http://example.com/image.png")
                        .data(data)
                        .topicName("medical_appointment")
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
    }
}
