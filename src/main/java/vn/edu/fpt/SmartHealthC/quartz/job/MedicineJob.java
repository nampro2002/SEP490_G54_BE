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
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.DeviceNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.TopicNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicineRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.RefreshToken;
import vn.edu.fpt.SmartHealthC.quartz.quartzService.TriggerExecutionService;
import vn.edu.fpt.SmartHealthC.repository.MedicineRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.RefreshTokenRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicineRecordService;
import vn.edu.fpt.SmartHealthC.serivce.NotificationService;
import vn.edu.fpt.SmartHealthC.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MedicineJob implements Job {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private TriggerExecutionService triggerExecutionService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private MedicineRecordRepository medicineRecordRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Medicine Job is running");
        Date date = null;
        try {
            date = DateUtils.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findByTime(date);
        System.out.println("Medicine Job find " + medicineRecordList.size() + " records");
        if (medicineRecordList.isEmpty()) {
            return;
        }
        for (MedicineRecord record : medicineRecordList) {
            List<RefreshToken> refreshToken = refreshTokenRepository.findRecordByAccountId(record.getAppUserId().getAccountId().getId());
            if (refreshToken.isEmpty()) {
                return;
            }
            String title;
            String body;
            for (RefreshToken token : refreshToken) {
                if (token.getLanguage().equals(TypeLanguage.KR)) {
                    title = "스마트 헬싱 C";
                    body = "약을 복용할 시간입니다! " + record.getMedicineType().getTitle();
                } else {
                    title = "Smart Healthing C";
                    body = "It's time to take your medicine! " + record.getMedicineType().getTitleEn();
                }
                HashMap<String, String> data = new HashMap<>();
                DeviceNotificationRequest deviceNotificationRequest = DeviceNotificationRequest.builder()
                        .title(title)
                        .body(body)
                        .imageUrl("")
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
        System.out.println("Medicine Job is done");
    }
}