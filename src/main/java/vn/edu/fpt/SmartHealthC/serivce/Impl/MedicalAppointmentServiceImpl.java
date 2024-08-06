package vn.edu.fpt.SmartHealthC.serivce.Impl;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointmentStatus;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeNotification;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentByWebUserDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.notificationDTO.DeviceNotificationRequest;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicalAppointmentRepository;
import vn.edu.fpt.SmartHealthC.repository.RefreshTokenRepository;
import vn.edu.fpt.SmartHealthC.serivce.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class MedicalAppointmentServiceImpl implements MedicalAppointmentService {

    @Autowired
    private MedicalAppointmentRepository medicalAppointmentRepository;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private WebUserService webUserService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public MedicalAppointmentResponseDTO createMedicalAppointment(MedicalAppointmentDTO medicalAppointmentDTO) {
        MedicalAppointment medicalAppointment = MedicalAppointment.builder()
                .typeMedicalAppointment(medicalAppointmentDTO.getType())
                .hospital(medicalAppointmentDTO.getLocation())
                .date(medicalAppointmentDTO.getDate())
                .statusMedicalAppointment(TypeMedicalAppointmentStatus.PENDING)
                .note(medicalAppointmentDTO.getNote())
                .build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        AppUser appUser = appUserService.findAppUserByEmail(email);
        medicalAppointment.setAppUserId(appUser);
        medicalAppointment = medicalAppointmentRepository.save(medicalAppointment);
        MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                .id(medicalAppointment.getId())
                .appUserName(medicalAppointment.getAppUserId().getName())
                .date(medicalAppointment.getDate())
                .hospital(medicalAppointment.getHospital())
                .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                .statusMedicalAppointment(medicalAppointment.getStatusMedicalAppointment())
                .note(medicalAppointment.getNote())
                .result(medicalAppointment.getResult())
                .build();
        return medicalAppointmentResponseDTO;
    }

    @Override
    public MedicalAppointmentResponseDTO createMedicalAppointmentByWebUser(MedicalAppointmentByWebUserDTO medicalAppointmentDTO) {
        MedicalAppointment medicalAppointment = MedicalAppointment.builder()
                .typeMedicalAppointment(medicalAppointmentDTO.getType())
                .hospital(medicalAppointmentDTO.getLocation())
                .date(medicalAppointmentDTO.getDate())
                .statusMedicalAppointment(TypeMedicalAppointmentStatus.CONFIRM)
                .note(medicalAppointmentDTO.getNote())
                .build();

        Optional<AppUser> appUser = appUserService.findAppUserEntityById(medicalAppointmentDTO.getPatientId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        medicalAppointment.setAppUserId(appUser.get());
        medicalAppointment = medicalAppointmentRepository.save(medicalAppointment);
        MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                .id(medicalAppointment.getId())
                .appUserName(medicalAppointment.getAppUserId().getName())
                .date(medicalAppointment.getDate())
                .hospital(medicalAppointment.getHospital())
                .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                .statusMedicalAppointment(medicalAppointment.getStatusMedicalAppointment())
                .note(medicalAppointment.getNote())
                .result(medicalAppointment.getResult())
                .build();
        NotificationSetting notificationSetting = notificationService.findByAccountIdAndType(appUser.get().getAccountId().getId(), TypeNotification.MEDICAL_APPOINTMENT_NOTIFICATION);
        String title = "Smart Healthing C";
        String body = "Your medical specialist has create a health check scheduled " + medicalAppointmentResponseDTO.getTypeMedicalAppointment() + "for you";
        Map<String, String> data = new HashMap<>();
        if (notificationSetting.isStatus()) {
            List<RefreshToken> refreshTokenList = refreshTokenRepository.findRecordByAccountId(appUser.get().getAccountId().getId());
            for (RefreshToken refreshToken : refreshTokenList) {
                if (refreshToken.getLanguage().equals(TypeLanguage.KR)) {
                    title = "스마트 헬싱 C";
                    body = "귀하의 의료 전문가가 예약된 건강 검진을 만들었습니다" + medicalAppointmentResponseDTO.getTypeMedicalAppointment() + "당신을 위해";
                }
                try {
                    notificationService.sendNotificationToDevice(DeviceNotificationRequest.builder()
                            .deviceToken(refreshToken.getDeviceToken())
                            .title(title)
                            .body(body)
                            .data(data)
                            .build());
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
        return medicalAppointmentResponseDTO;
    }


    @Override
    public MedicalAppointment getMedicalAppointmentEntityById(Integer id) {
        Optional<MedicalAppointment> medicalAppointment = medicalAppointmentRepository.findById(id);
        if (medicalAppointment.isEmpty()) {
            throw new AppException(ErrorCode.MEDICAL_APPOINTMENT_NOT_FOUND);
        }
        return medicalAppointment.get();
    }

    @Override
    public MedicalAppointmentResponseDTO getMedicalAppointmentById(Integer id) {
        Optional<MedicalAppointment> medicalAppointment = medicalAppointmentRepository.findById(id);
        if (medicalAppointment.isEmpty()) {
            throw new AppException(ErrorCode.MEDICAL_APPOINTMENT_NOT_FOUND);
        }
        MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                .id(medicalAppointment.get().getId())
                .appUserName(medicalAppointment.get().getAppUserId().getName())
                .date(medicalAppointment.get().getDate())
                .hospital(medicalAppointment.get().getHospital())
                .typeMedicalAppointment(medicalAppointment.get().getTypeMedicalAppointment())
                .statusMedicalAppointment(medicalAppointment.get().getStatusMedicalAppointment())
                .note(medicalAppointment.get().getNote())
                .result(medicalAppointment.get().getResult())
                .build();
        return medicalAppointmentResponseDTO;

    }

    @Override
    public ResponsePaging<List<MedicalAppointmentResponseDTO>> getAllMedicalAppointments(Integer pageNo, String search) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        WebUser webUser = webUserService.getWebUserByEmail(email);
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<MedicalAppointment> pagedResult = medicalAppointmentRepository.findAllByWebUserId(webUser.getId(), paging, search.toLowerCase());
        List<MedicalAppointment> medicalAppointmentList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            medicalAppointmentList = pagedResult.getContent();
        }
        List<MedicalAppointmentResponseDTO> responseDTOList = new ArrayList<>();
        for (MedicalAppointment medicalAppointment : medicalAppointmentList) {
            MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                    .id(medicalAppointment.getId())
                    .appUserName(medicalAppointment.getAppUserId().getName())
                    .date(medicalAppointment.getDate())
                    .hospital(medicalAppointment.getHospital())
                    .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                    .statusMedicalAppointment(medicalAppointment.getStatusMedicalAppointment())
                    .note(medicalAppointment.getNote())
                    .result(medicalAppointment.getResult())
                    .build();
            responseDTOList.add(medicalAppointmentResponseDTO);
        }
        return ResponsePaging.<List<MedicalAppointmentResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(responseDTOList)
                .build();
    }

    @Override
    public MedicalAppointmentResponseDTO updateMedicalAppointment(Integer id, MedicalAppointmentUpdateDTO medicalAppointmentDTO) {
        MedicalAppointment medicalAppointment = getMedicalAppointmentEntityById(id);
        medicalAppointment.setTypeMedicalAppointment(medicalAppointmentDTO.getType());
        medicalAppointment.setDate(medicalAppointmentDTO.getDate());
        medicalAppointment.setHospital(medicalAppointmentDTO.getLocation());
        medicalAppointment.setNote(medicalAppointmentDTO.getNote());
        medicalAppointment.setResult(medicalAppointmentDTO.getResult());
        TypeMedicalAppointmentStatus typeSave = TypeMedicalAppointmentStatus.DONE;
        if (medicalAppointment.getStatusMedicalAppointment().equals(TypeMedicalAppointmentStatus.PENDING)) {
            typeSave = TypeMedicalAppointmentStatus.CONFIRM;
        } else if (medicalAppointment.getStatusMedicalAppointment().equals(TypeMedicalAppointmentStatus.CONFIRM)) {
            typeSave = TypeMedicalAppointmentStatus.DONE;
        }
        medicalAppointment.setStatusMedicalAppointment(typeSave);
        medicalAppointment = medicalAppointmentRepository.save(medicalAppointment);

        MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                .id(medicalAppointment.getId())
                .appUserName(medicalAppointment.getAppUserId().getName())
                .date(medicalAppointment.getDate())
                .hospital(medicalAppointment.getHospital())
                .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                .statusMedicalAppointment(typeSave)
                .note(medicalAppointment.getNote())
                .result(medicalAppointment.getResult())
                .build();
        NotificationSetting notificationSetting = notificationService.findByAccountIdAndType(medicalAppointment.getAppUserId().getAccountId().getId(), TypeNotification.MEDICAL_APPOINTMENT_NOTIFICATION);
        Map<String, String> data = new HashMap<>();
        if (notificationSetting.isStatus()) {
            List<RefreshToken> refreshTokenList = refreshTokenRepository.findRecordByAccountId(medicalAppointment.getAppUserId().getAccountId().getId());
            String title = "Smart Healthing C";
            String body = "Your medical appointment " + medicalAppointmentResponseDTO.getTypeMedicalAppointment() + "has updated " + medicalAppointmentResponseDTO.getStatusMedicalAppointment();
            for (RefreshToken refreshToken : refreshTokenList) {
                if (refreshToken.getLanguage().equals(TypeLanguage.KR)) {
                    title = "스마트 헬싱 C";
                    body = "귀하의 의료 약속 " + medicalAppointmentResponseDTO.getTypeMedicalAppointment() + "가 업데이트되었습니다 " + medicalAppointmentResponseDTO.getStatusMedicalAppointment();
                }
                try {
                    notificationService.sendNotificationToDevice(DeviceNotificationRequest.builder()
                            .deviceToken(refreshToken.getDeviceToken())
                            .title(title)
                            .body(body)
                            .data(data)
                            .build());
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

        return medicalAppointmentResponseDTO;
    }

    @Override
    public MedicalAppointmentResponseDTO deleteMedicalAppointment(Integer id) {
        MedicalAppointment medicalAppointment = getMedicalAppointmentEntityById(id);
        medicalAppointmentRepository.deleteById(id);
        MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                .id(medicalAppointment.getId())
                .appUserName(medicalAppointment.getAppUserId().getName())
                .date(medicalAppointment.getDate())
                .hospital(medicalAppointment.getHospital())
                .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                .statusMedicalAppointment(medicalAppointment.getStatusMedicalAppointment())
                .note(medicalAppointment.getNote())
                .result(medicalAppointment.getResult())
                .build();
        return medicalAppointmentResponseDTO;
    }

    @Override
    public ResponsePaging<List<MedicalAppointmentResponseDTO>> getAllMedicalAppointmentsPending(Integer pageNo, TypeMedicalAppointment type) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        WebUser webUser = webUserService.getWebUserByEmail(email);
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<MedicalAppointment> pagedResult = medicalAppointmentRepository.findAllPendingByUserIdAndType(TypeMedicalAppointmentStatus.PENDING, webUser.getId(), type, paging);
        List<MedicalAppointment> medicalAppointmentList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            medicalAppointmentList = pagedResult.getContent();
        }
        List<MedicalAppointmentResponseDTO> listResponse = medicalAppointmentList.stream()
//                .filter(record -> (record.getStatusMedicalAppointment().equals(TypeMedicalAppointmentStatus.PENDING) &&
//                        record.getAppUserId().getId() == id))
                .map(record -> {
                    MedicalAppointmentResponseDTO dto = new MedicalAppointmentResponseDTO();
                    dto.setId(record.getId());
                    dto.setAppUserName(record.getAppUserId().getName());
                    dto.setDate(record.getDate());
                    dto.setHospital(record.getHospital());
                    dto.setTypeMedicalAppointment(record.getTypeMedicalAppointment());
                    dto.setStatusMedicalAppointment(record.getStatusMedicalAppointment());
                    dto.setResult(record.getResult());
                    dto.setNote(record.getNote());
                    return dto;
                })
                .toList();
        return ResponsePaging.<List<MedicalAppointmentResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(listResponse)
                .build();
    }


    @Override
    public List<MedicalAppointmentResponseDTO> getMedicalAppointmentByUserIdMobile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        AppUser appUser = appUserService.findAppUserByEmail(email);
        List<MedicalAppointment> medicalAppointmentList = medicalAppointmentRepository.findAllByUserIdAndType(TypeMedicalAppointmentStatus.DONE, appUser.getId());
        List<MedicalAppointmentResponseDTO> responseDTOList = new ArrayList<>();
        for (MedicalAppointment medicalAppointment : medicalAppointmentList) {
            MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                    .id(medicalAppointment.getId())
                    .appUserName(medicalAppointment.getAppUserId().getName())
                    .date(medicalAppointment.getDate())
                    .hospital(medicalAppointment.getHospital())
                    .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                    .statusMedicalAppointment(medicalAppointment.getStatusMedicalAppointment())
                    .note(medicalAppointment.getNote())
                    .result(medicalAppointment.getResult())
                    .build();
            responseDTOList.add(medicalAppointmentResponseDTO);
        }
        return responseDTOList;
    }

    @Override
    public ResponsePaging<List<MedicalAppointmentResponseDTO>> getMedicalAppointmentByUserId(Integer userId, Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<MedicalAppointment> pagedResult = medicalAppointmentRepository.findAllByAppUserId(userId, paging);
        List<MedicalAppointment> medicalAppointmentList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            medicalAppointmentList = pagedResult.getContent();
        }
        List<MedicalAppointmentResponseDTO> responseDTOList = new ArrayList<>();
        for (MedicalAppointment medicalAppointment : medicalAppointmentList) {
            MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                    .id(medicalAppointment.getId())
                    .appUserName(medicalAppointment.getAppUserId().getName())
                    .date(medicalAppointment.getDate())
                    .hospital(medicalAppointment.getHospital())
                    .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                    .statusMedicalAppointment(medicalAppointment.getStatusMedicalAppointment())
                    .note(medicalAppointment.getNote())
                    .result(medicalAppointment.getResult())
                    .build();
            responseDTOList.add(medicalAppointmentResponseDTO);
        }
        return ResponsePaging.<List<MedicalAppointmentResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(responseDTOList)
                .build();
    }

    @Override
    public List<MedicalAppointment> getMedicalAppointmentConfirm() {
        return medicalAppointmentRepository.findAllByType(TypeMedicalAppointmentStatus.CONFIRM);

    }

    @Override
    public List<ListPatientResponseDTO> getListPatientByWebUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        WebUser webUser = webUserService.getWebUserByEmail(email);
        List<AppUser> appUserList = appUserService.findAllByWebUserId(webUser.getId());
        List<ListPatientResponseDTO> listPatientResponseDTOList = new ArrayList<>();
        for (AppUser appUser : appUserList) {
            ListPatientResponseDTO listPatientResponseDTO = ListPatientResponseDTO.builder()
                    .id(appUser.getId())
                    .patientName(appUser.getName())
                    .build();
            listPatientResponseDTOList.add(listPatientResponseDTO);
        }
        return listPatientResponseDTOList;
    }


}