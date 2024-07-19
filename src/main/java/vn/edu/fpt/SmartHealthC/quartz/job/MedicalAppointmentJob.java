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
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNotification;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.DeviceNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.entity.NotificationSetting;
import vn.edu.fpt.SmartHealthC.domain.entity.RefreshToken;
import vn.edu.fpt.SmartHealthC.repository.RefreshTokenRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicalAppointmentService;
import vn.edu.fpt.SmartHealthC.serivce.Impl.NotificationServiceImpl;
import vn.edu.fpt.SmartHealthC.serivce.NotificationService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
@Slf4j
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MedicalAppointmentJob implements Job {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private MedicalAppointmentService medicalAppointmentService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Executing Medical Reminder Job at: " + new Date());
        List<MedicalAppointment> medicalAppointments = medicalAppointmentService.getMedicalAppointmentConfirm();
        for (MedicalAppointment item : medicalAppointments) {
            Instant instant = item.getDate().toInstant();

            // Convert Instant to LocalDateTime considering system default zone
            LocalDate localDateTime = LocalDate.ofInstant(instant, ZoneId.systemDefault());
            LocalDate currentDate = LocalDate.now();
            long daysBetween = ChronoUnit.DAYS.between(currentDate, localDateTime);
            System.out.println("Days between Medical Appointment: " + daysBetween);
            //if daysBetween = 1, 3, 7
            if (daysBetween == 1 || daysBetween == 3 || daysBetween == 7) {
                NotificationSetting notificationSetting = notificationService.findByAccountIdAndType(item.getAppUserId().getAccountId().getId(), TypeNotification.MEDICAL_APPOINTMENT_NOTIFICATION);
                if(notificationSetting == null || !notificationSetting.isStatus()){
                    continue;
                }
                List<RefreshToken> refreshToken = refreshTokenRepository.findRecordByAccountId(item.getAppUserId().getAccountId().getId());
                for (RefreshToken token : refreshToken) {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("key1", "value1");
                    DeviceNotificationRequest deviceNotificationRequest = DeviceNotificationRequest.builder()
                            .title("Medical Appointment")
                            .body("You have a medical appointment in " + daysBetween + " days.")
                            .imageUrl("http://example.com/image.png")
                            .data(data)
                            .deviceToken(token.getDeviceToken())
                            .build();
                    try {
                        notificationService.sendNotificationToDevice(deviceNotificationRequest);
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
    }
}
