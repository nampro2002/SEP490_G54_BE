package vn.edu.fpt.SmartHealthC.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ChangeLanguageNotiRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.NotificationSettingRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.NotificationStatusRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.AllDevicesNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.DeviceNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.NotificationSubscriptionRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.TopicNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.NotificationSettingResponseDTO;
import vn.edu.fpt.SmartHealthC.serivce.NotificationService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/send-to-device")
    public ApiResponse<?> sendNotification(@RequestBody @Valid DeviceNotificationRequest request) {
        try {
            notificationService.sendNotificationToDevice(request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<DeviceNotificationRequest>builder()
                            .code(HttpStatus.OK.value())
                            .result(request)
                            .build()).getBody();
        } catch (FirebaseMessagingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<DeviceNotificationRequest>builder()
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Failed to send notification.")
                            .build()).getBody();
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/send-to-topic")
    public ApiResponse<?> sendNotificationToTopic(@RequestBody @Valid TopicNotificationRequest request) {
        try {
            notificationService.sendPushNotificationToTopic(request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<TopicNotificationRequest>builder()
                            .code(HttpStatus.OK.value())
                            .result(request)
                            .build()).getBody();
        } catch (FirebaseMessagingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<DeviceNotificationRequest>builder()
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Failed to send notification.")
                            .build()).getBody();
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    // not used
    @PostMapping("/send-to-all")
    public ApiResponse<?> sendNotificationToAll(@RequestBody @Valid AllDevicesNotificationRequest request) {
        try {
            notificationService.sendMulticastNotification(request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<AllDevicesNotificationRequest>builder()
                            .code(HttpStatus.OK.value())
                            .result(request)
                            .build()).getBody();
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<DeviceNotificationRequest>builder()
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Failed to send multicast notification.")
                            .build()).getBody();
        }
    }

    @PostMapping("/subscribe")
    public ApiResponse<?> subscribeToTopic(@RequestBody @Valid NotificationSubscriptionRequest request) {
        try {
            notificationService.subscribeDeviceToTopic(request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<String>builder()
                            .code(HttpStatus.OK.value())
                            .result(request.getTopicName())
                            .build()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<DeviceNotificationRequest>builder()
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Failed to subscribe device to the topic.")
                            .build()).getBody();
        }
    }

    @PostMapping("/unsubscribe")
    public ApiResponse<?> unsubscribeFromTopic(@RequestBody @Valid NotificationSubscriptionRequest request) {
        try {
            notificationService.unsubscribeDeviceFromTopic(request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<String>builder()
                            .code(HttpStatus.OK.value())
                            .result(request.getTopicName())
                            .build()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<DeviceNotificationRequest>builder()
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Failed to unsubscribe device to the topic.")
                            .build()).getBody();
        }
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("mobile/status")
    public ApiResponse<?> settingNotification(@RequestBody @Valid NotificationSettingRequestDTO request) {
        try {
            notificationService.settingNotification(request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<String>builder()
                            .code(HttpStatus.OK.value())
                            .message("Setting notification successfully.")
                            .build()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<DeviceNotificationRequest>builder()
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Failed to unsubscribe device to the topic.")
                            .build()).getBody();
        }
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("mobile/get-all")
    public ApiResponse<?> getNotificationStatusUser() {
        try {

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.<List<NotificationSettingResponseDTO>>builder()
                            .code(HttpStatus.OK.value())
                            .result(notificationService.getNotificationStatusUser())
                            .build()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<DeviceNotificationRequest>builder()
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Failed to get notification status.")
                            .build()).getBody();
        }
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("mobile/change-language")
    public ApiResponse<?> changeLanguage(@RequestBody @Valid ChangeLanguageNotiRequestDTO request) {
        try {
            notificationService.changeLanguage(request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.builder()
                            .code(HttpStatus.OK.value())
                            .message("Change language successfully.")
                            .build()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<DeviceNotificationRequest>builder()
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Failed to change language.")
                            .build()).getBody();
        }
    }

//    @PutMapping("/sync")
//    public ApiResponse<?> updateStatusNotification() {
//        try {
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(ApiResponse.<List<NotificationSettingResponseDTO> >builder()
//                            .code(HttpStatus.OK.value())
//                            .result(notificationService.updateStatusNotification())
//                            .build()).getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ApiResponse.<DeviceNotificationRequest>builder()
//                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                            .message("Failed to unsubscribe device to the topic.")
//                            .build()).getBody();
//        }
//    }
}