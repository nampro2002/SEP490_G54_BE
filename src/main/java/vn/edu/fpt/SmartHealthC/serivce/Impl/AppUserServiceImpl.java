package vn.edu.fpt.SmartHealthC.serivce.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeAccount;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeLanguage;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalHistory;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AppUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.AssignRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalHistory;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.repository.UserMedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;
import vn.edu.fpt.SmartHealthC.serivce.WebUserService;
import vn.edu.fpt.SmartHealthC.utils.AccountUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    private final WebUserService webUserService;
    private final UserMedicalHistoryRepository userMedicalHistoryRepository;

    @Override
    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUserDetailResponseDTO getAppUserDetailById(Integer id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.APP_USER_NOT_FOUND));
        //map appUser to AppUserResponseDTO
        AppUserDetailResponseDTO dto = new AppUserDetailResponseDTO();
        dto.setAccountId(appUser.getAccountId().getId());
        dto.setEmail(appUser.getAccountId().getEmail());
        dto.setAppUserId(appUser.getId());
        dto.setName(appUser.getName());
        dto.setHeight(appUser.getHeight());
        dto.setWeight(appUser.getWeight());
        dto.setCic(appUser.getCic());
        dto.setDob(appUser.getDob());
        dto.setGender(appUser.isGender());
        dto.setPhoneNumber(appUser.getPhoneNumber());
        if (appUser.getWebUser() != null) {
            dto.setMsName(appUser.getWebUser().getUserName());
        }else{
            dto.setMsName("not assigned yet");
        }
        StringBuilder chronicDiseases = new StringBuilder();
        appUser.getUserMedicalHistoryList().forEach(userMedicalHistory -> {
            if (!userMedicalHistory.getConditionId().getType().equals(TypeMedicalHistory.OTHERS)
                    || !userMedicalHistory.getConditionId().getType().equals(TypeMedicalHistory.HABIT)) {
                if (!chronicDiseases.isEmpty()) {
                    chronicDiseases.append("/");
                }
                chronicDiseases.append(userMedicalHistory.getConditionId().getName());
            }
        });
        dto.setChronicDiseases(String.valueOf(chronicDiseases));
        StringBuilder otherDiseases = new StringBuilder();
        appUser.getUserMedicalHistoryList().forEach(userMedicalHistory -> {
            if (userMedicalHistory.getConditionId().getType().equals(TypeMedicalHistory.OTHERS)) {
                if (!otherDiseases.isEmpty()) {
                    otherDiseases.append("/");
                }
                otherDiseases.append(userMedicalHistory.getConditionId().getName());
            }
        });
        dto.setOthersDiseases(String.valueOf(otherDiseases));
        Boolean smoke = false;
        for (int i = 0; i < appUser.getUserMedicalHistoryList().size(); i++) {
            if (appUser.getUserMedicalHistoryList().get(i).getConditionId().getName().equalsIgnoreCase("Hút thuốc")) {
                smoke = true;
            }
        }
        dto.setSmoke(smoke);
        Boolean alcohol = false;
        for (int i = 0; i < appUser.getUserMedicalHistoryList().size(); i++) {
            if (appUser.getUserMedicalHistoryList().get(i).getConditionId().getName().equalsIgnoreCase("Uống rượu")) {
                smoke = true;
            }
        }
        dto.setSmoke(smoke);
        dto.setAlcohol(alcohol);

        //calculate BMI
        Float bmi = appUser.getWeight() / (appUser.getHeight() * appUser.getHeight());
        dto.setBmi(bmi);
        return dto;
    }


//    @Override
//    public List<AppUserResponseDTO> getListAppUser(Integer pageNo) {
//        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
//        Page<AppUser> pagedResult = appUserRepository.findAll(paging);
//        List<AppUser> appUserList = new ArrayList<>();
//        if(pagedResult.hasContent()) {
//            appUserList = pagedResult.getContent();
//        }
//        return appUserList.stream()
//                .map(record -> {
//                    AppUserResponseDTO dto = new AppUserResponseDTO();
//                    dto.setAccountId(record.getAccountId().getId());
//                    dto.setEmail(record.getAccountId().getEmail());
//                    dto.setAppUserId(record.getId());
//                    dto.setName(record.getName());
//                    dto.setHospitalNumber(record.getHospitalNumber());
//                    dto.setDob(record.getDob());
//                    dto.setGender(record.isGender());
//                    dto.setPhoneNumber(record.getPhoneNumber());
//                    return dto;
//                })
//                .toList();
//    }

    @Override
    public ResponsePaging<List<AppUserResponseDTO>> getListAppUser(Integer pageNo, String search) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        WebUser webUser = webUserService.getWebUserByEmail(email);
        List<AppUser> appUserList = new ArrayList<>();
        Pageable paging = PageRequest.of(pageNo, 5);
        Page<AppUser> pagedResult;
        if (webUser.getAccountId().getType() == TypeAccount.MEDICAL_SPECIALIST) {
            pagedResult = appUserRepository.findAllByUserId(webUser.getId(), search.toLowerCase(), paging);
        } else {
            pagedResult = appUserRepository.findAllActiveAndNotDeletedPaging(search.toLowerCase(), paging);
        }
        if (pagedResult.hasContent()) {
            appUserList = pagedResult.getContent();
        }
        List<AppUserResponseDTO> appUserResponseDTOList = appUserList.stream()
                .map(record -> {
                    AppUserResponseDTO dto = new AppUserResponseDTO();
                    dto.setAccountId(record.getAccountId().getId());
                    dto.setEmail(record.getAccountId().getEmail());
                    dto.setAppUserId(record.getId());
                    dto.setName(record.getName());
                    dto.setCic(record.getCic());
                    dto.setDob(record.getDob());
                    dto.setGender(record.isGender());
                    dto.setPhoneNumber(record.getPhoneNumber());
                    return dto;
                })
                .toList();
        return ResponsePaging.<List<AppUserResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(appUserResponseDTOList)
                .build();
    }

    @Override
    public AppUserDetailResponseDTO updateAppUser(Integer id, AppUserRequestDTO appUserRequestDTO) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.APP_USER_NOT_FOUND)
        );
        appUser.setName(appUserRequestDTO.getName());
        appUser.setDob(appUserRequestDTO.getDob());
        appUser.setGender(appUserRequestDTO.getGender());
        appUser.setPhoneNumber(appUserRequestDTO.getPhoneNumber());
        appUser.setWeight(appUserRequestDTO.getWeight());
        appUser.setHeight(appUserRequestDTO.getHeight());
        appUser.setCic(appUserRequestDTO.getCic());
        appUserRepository.save(appUser);
        return getAppUserDetailById(id);
    }

    @Override
    public AppUser findAppUserByEmail(String email) {
        AppUser appUser = appUserRepository.findByAccountEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.APP_USER_NOT_FOUND)
        );
        return appUser;
    }

    @Override
    public Optional<AppUser> findAppUserEntityById(Integer id) {
        return appUserRepository.findById(id);
    }

    @Override
    public List<AppUser> getListAppUser() {
        return appUserRepository.findAllValidUser();
    }

    @Override
    public List<AppUser> findAllByWebUserId(Integer id) {
        return appUserRepository.findAllByWebUserId(id);
    }

    @Override
    public AppUserNameHeightWeightResponseDTO getAppUserNameHeightWeight(TypeLanguage language) {
        AppUser appUser = AccountUtils.getAccountAuthen(appUserRepository);
        Optional<UserMedicalHistory> userMedicalHistory = userMedicalHistoryRepository.findByAppUser(appUser.getId());
        if(userMedicalHistory.isPresent()) {
            return  new AppUserNameHeightWeightResponseDTO()
                    .builder()
                    .name(appUser.getName())
                    .height(appUser.getHeight())
                    .weight(appUser.getWeight())
                    .medicalUser(new MedicalHistoryAppUserResponseDTO().builder()
                            .id(userMedicalHistory.get().getConditionId().getId())
                            .name((language != TypeLanguage.EN) ? userMedicalHistory.get().getConditionId().getName() : userMedicalHistory.get().getConditionId().getNameEn()).build())
                    .build();
        }
        return  new AppUserNameHeightWeightResponseDTO()
                .builder()
                .name(appUser.getName())
                .height(appUser.getHeight())
                .weight(appUser.getWeight())
                .medicalUser(new MedicalHistoryAppUserResponseDTO().builder()
                        .id(0)
                        .name("Not found").build())
                .build();
    }

    @Override
    public AppUserAssignResponseDTO assignPatientToMs(AssignRequestDTO assignRequestDTO) {
        AppUser appUser = appUserRepository.findByIdActivated(assignRequestDTO.getAppUserId()).orElseThrow(
                () -> new AppException(ErrorCode.APP_USER_NOT_FOUND_OR_NOT_ACTIVATED)
        );
            WebUser webUser = webUserService.getWebUserById(assignRequestDTO.getWebUserId());
        if (!webUser.getAccountId().getType().equals(TypeAccount.MEDICAL_SPECIALIST)) {
            throw new AppException(ErrorCode.WEB_USER_NOT_VALID);
        }
        if (webUser.getAccountId().isDeleted()) {
            throw new AppException(ErrorCode.WEB_USER_NOT_FOUND);
        }
        appUser.setWebUser(webUser);
        appUserRepository.save(appUser);
        return AppUserAssignResponseDTO.builder()
                .appUserId(appUser.getId())
                .accountId(appUser.getAccountId().getId())
                .email(appUser.getAccountId().getEmail())
                .name(appUser.getName())
                .cic(appUser.getCic())
                .dob(appUser.getDob())
                .gender(appUser.isGender())
                .phoneNumber(appUser.getPhoneNumber())
                .msName(webUser.getUserName())
                .build();
    }
}
