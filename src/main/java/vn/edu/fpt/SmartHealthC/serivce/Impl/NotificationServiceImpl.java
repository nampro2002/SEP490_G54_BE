package vn.edu.fpt.SmartHealthC.serivce.Impl;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNotification;
import vn.edu.fpt.SmartHealthC.domain.dto.request.NotificationSettingRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.AllDevicesNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.DeviceNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.NotificationSubscriptionRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.TopicNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.response.NotificationSettingResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.NotificationSetting;
import vn.edu.fpt.SmartHealthC.domain.entity.RefreshToken;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.NotificationSettingRepository;
import vn.edu.fpt.SmartHealthC.repository.RefreshTokenRepository;
import vn.edu.fpt.SmartHealthC.serivce.NotificationService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final FirebaseApp firebaseApp;
    private final NotificationSettingRepository notificationSettingRepository;
    private final AppUserRepository appUserRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public void sendNotificationToDevice(DeviceNotificationRequest request) throws FirebaseMessagingException, ExecutionException, InterruptedException {
        Message fcmMessage = Message.builder()
                .setToken(request.getDeviceToken())
                .setNotification(
                        Notification.builder()
                                .setTitle(request.getTitle())
                                .setBody(request.getBody())
                                .setImage(request.getImageUrl())
                                .build()
                )
                .putAllData(request.getData())
                .build();

        String response = FirebaseMessaging.getInstance(firebaseApp).sendAsync(fcmMessage).get();
        log.info("sendNotificationToDevice response: {}", response);
    }

    public void sendPushNotificationToTopic(TopicNotificationRequest request) throws FirebaseMessagingException, ExecutionException, InterruptedException {
        Message fcmMessage = Message.builder()
                .setTopic(request.getTopicName())
                .setNotification(
                        Notification.builder()
                                .setTitle(request.getTitle())
                                .setBody(request.getBody())
                                .setImage(request.getImageUrl())
                                .build()
                )
                .setAndroidConfig(getAndroidConfig(request.getTopicName()))
                .setApnsConfig(getApnsConfig(request.getTopicName()))
                .putAllData(request.getData())
                .build();

        String response = FirebaseMessaging.getInstance(firebaseApp).sendAsync(fcmMessage).get();
        log.info("sendNotificationToDevice response: {}", response);
    }


    public void sendMulticastNotification(AllDevicesNotificationRequest request) throws FirebaseMessagingException {
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .addAllTokens(request.getDeviceTokenList().isEmpty() ? getAllDeviceTokens() : request.getDeviceTokenList())
                .setNotification(
                        Notification.builder()
                                .setTitle(request.getTitle())
                                .setBody(request.getBody())
                                .setImage(request.getImageUrl())
                                .build()
                )
                .putAllData(request.getData())
                .build();

        BatchResponse response = FirebaseMessaging.getInstance(firebaseApp).sendEachForMulticast(multicastMessage);
        // Process the response
        for (SendResponse sendResponse : response.getResponses()) {
            if (sendResponse.isSuccessful()) {
                log.info("Message sent successfully to: {}", sendResponse.getMessageId());
            } else {
                log.info("Failed to send message to: {}", sendResponse.getMessageId());
                log.error("Error details: {}", sendResponse.getException().getMessage());
            }
        }
    }

    public void subscribeDeviceToTopic(NotificationSubscriptionRequest request) throws FirebaseMessagingException {
        FirebaseMessaging.getInstance().subscribeToTopic(
                Collections.singletonList(request.getDeviceToken()),
                request.getTopicName()
        );
    }

    public void unsubscribeDeviceFromTopic(NotificationSubscriptionRequest request) throws FirebaseMessagingException {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(
                Collections.singletonList(request.getDeviceToken()),
                request.getTopicName()
        );
    }

    private List<String> getAllDeviceTokens() {
        // Implement logic to retrieve all device tokens from your database or storage
        // Return a list of device tokens
        return new ArrayList<>();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound("default")
                        .setColor("#FFFF00").setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }
    //call api
    public void updateStatusNotification(String email, String deviceToken) {
        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        List<RefreshToken> refreshToken = refreshTokenRepository.findRecordByAccountId(appUser.get().getAccountId().getId());
        List<NotificationSetting> notificationSettingList = notificationSettingRepository.findByAccountId(appUser.get().getAccountId().getId());
        for (NotificationSetting notificationSetting : notificationSettingList) {
            if (notificationSetting.getTypeNotification().equals(TypeNotification.DAILY_NOTIFICATION)) {
                NotificationSubscriptionRequest notificationSubscriptionRequest = NotificationSubscriptionRequest.builder().deviceToken(deviceToken).topicName("daily").build();
                changeStatusDeviceToTopic(notificationSetting, notificationSubscriptionRequest);
            }
            if (notificationSetting.getTypeNotification().equals(TypeNotification.PLAN_NOTIFICATION)) {
                NotificationSubscriptionRequest notificationSubscriptionRequest = NotificationSubscriptionRequest.builder().deviceToken(deviceToken).topicName("mondayam").build();
                changeStatusDeviceToTopic(notificationSetting, notificationSubscriptionRequest);
            }
            if (notificationSetting.getTypeNotification().equals(TypeNotification.WEEKLY_REPORT_NOTIFICATION)) {
                NotificationSubscriptionRequest notificationSubscriptionRequest = NotificationSubscriptionRequest.builder().deviceToken(deviceToken).topicName("sundaypm").build();
                changeStatusDeviceToTopic(notificationSetting, notificationSubscriptionRequest);
            }
        }
//        return notificationSettingList.stream()
//                .map(record -> {
//                    NotificationSettingResponseDTO dto = new NotificationSettingResponseDTO();
//                    dto.setStatus(record.isStatus());
//                    dto.setTypeNotification(record.getTypeNotification());
//                    return dto;
//                })
//                .toList();
    }

    public void settingNotification(NotificationSettingRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        if (!request.getTypeNotification().equals(TypeNotification.ALL)) {
            NotificationSetting notificationSetting = findByAccountIdAndType(appUser.get().getAccountId().getId(), request.getTypeNotification());
            notificationSetting.setStatus(request.isStatus());
            notificationSettingRepository.save(notificationSetting);
            updateStatusNotification(email, request.getDeviceToken());
        } else {
            List<NotificationSetting> notificationSettingList = notificationSettingRepository.findByAccountId(appUser.get().getAccountId().getId());
            for (NotificationSetting notificationSetting : notificationSettingList) {
                notificationSetting.setStatus(request.isStatus());
                notificationSettingRepository.save(notificationSetting);
            }
            updateStatusNotification(email, request.getDeviceToken());
        }
    }

    private void changeStatusDeviceToTopic(NotificationSetting notificationSetting, NotificationSubscriptionRequest notificationSubscriptionRequest) {
        if (notificationSetting.isStatus()) {
            try {
                subscribeDeviceToTopic(notificationSubscriptionRequest);
            } catch (FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                unsubscribeDeviceFromTopic(notificationSubscriptionRequest);
            } catch (FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public List<NotificationSettingResponseDTO> getNotificationStatusUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<AppUser> appUser = appUserRepository.findByAccountEmail(email);
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        List<NotificationSetting> notificationSettingList = notificationSettingRepository.findByAccountId(appUser.get().getAccountId().getId());
        return notificationSettingList.stream()
                .map(record -> {
                    NotificationSettingResponseDTO dto = new NotificationSettingResponseDTO();
                    dto.setStatus(record.isStatus());
                    dto.setTypeNotification(record.getTypeNotification());
                    return dto;
                })
                .toList();
    }

    public void createRecordForAccount(Account account) {
        NotificationSetting daily = NotificationSetting.builder().accountId(account).typeNotification(TypeNotification.DAILY_NOTIFICATION).status(true).build();
        notificationSettingRepository.save(daily);
        NotificationSetting plan = NotificationSetting.builder().accountId(account).typeNotification(TypeNotification.PLAN_NOTIFICATION).status(true).build();
        notificationSettingRepository.save(plan);
        NotificationSetting weekly = NotificationSetting.builder().accountId(account).typeNotification(TypeNotification.WEEKLY_REPORT_NOTIFICATION).status(true).build();
        notificationSettingRepository.save(weekly);
        NotificationSetting monthly = NotificationSetting.builder().accountId(account).typeNotification(TypeNotification.MONTHLY_REPORT_NOTIFICATION).status(true).build();
        notificationSettingRepository.save(monthly);
        NotificationSetting question = NotificationSetting.builder().accountId(account).typeNotification(TypeNotification.QUESTION_NOTIFICATION).status(true).build();
        notificationSettingRepository.save(question);
        NotificationSetting medical = NotificationSetting.builder().accountId(account).typeNotification(TypeNotification.MEDICAL_APPOINTMENT_NOTIFICATION).status(true).build();
        notificationSettingRepository.save(medical);
    }

    @Override
    public NotificationSetting findByAccountIdAndType(Integer id, TypeNotification typeNotification) {
        Optional<NotificationSetting> notificationSettingOptional = notificationSettingRepository.findByAccountIdAndType(id, typeNotification);
        if (notificationSettingOptional.isEmpty()) {
            throw new AppException(ErrorCode.NOTIFICATION_SETTING_NOT_FOUND);
        }
        return notificationSettingOptional.get();
    }
}