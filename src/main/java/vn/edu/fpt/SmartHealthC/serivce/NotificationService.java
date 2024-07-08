package vn.edu.fpt.SmartHealthC.serivce;

import com.google.firebase.messaging.FirebaseMessagingException;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNotification;
import vn.edu.fpt.SmartHealthC.domain.dto.request.NotificationSettingRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.NotificationStatusRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.AllDevicesNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.DeviceNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.NotificationSubscriptionRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.TopicNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.response.NotificationSettingResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.Account;
import vn.edu.fpt.SmartHealthC.domain.entity.NotificationSetting;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface NotificationService {
    void sendNotificationToDevice(DeviceNotificationRequest deviceNotificationRequest)throws FirebaseMessagingException, ExecutionException, InterruptedException;

    void sendPushNotificationToTopic(TopicNotificationRequest topicNotificationRequest) throws FirebaseMessagingException, ExecutionException, InterruptedException ;

    List<NotificationSettingResponseDTO> getNotificationStatusUser();

    void settingNotification(NotificationSettingRequestDTO request);

    void unsubscribeDeviceFromTopic(NotificationSubscriptionRequest request) throws FirebaseMessagingException;

    void subscribeDeviceToTopic(NotificationSubscriptionRequest request) throws FirebaseMessagingException;

    void sendMulticastNotification(AllDevicesNotificationRequest request) throws FirebaseMessagingException;

    void createRecordForAccount(Account accountId);


    NotificationSetting findByAccountIdAndType(Integer id, TypeNotification typeNotification);

    void updateStatusNotification(String email, String deviceToken);
}
