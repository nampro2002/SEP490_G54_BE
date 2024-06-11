package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.UserMedicalHistoryDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.repository.UserMedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.serivce.UserMedicalHistoryService;

import java.util.List;
import java.util.Optional;

@Service
public class UserMedicalHistoryServiceImpl implements UserMedicalHistoryService {

    @Autowired
    private UserMedicalHistoryRepository userMedicalHistoryRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Override
    public UserMedicalHistory createUserMedicalHistory(UserMedicalHistoryDTO userMedicalHistoryDTO) {
        UserMedicalHistory userMedicalHistory =  UserMedicalHistory.builder()
                .build();
        Optional<AppUser> appUser = appUserRepository.findById(userMedicalHistoryDTO.getAppUserId());
        if(appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }
        Optional<MedicalHistory> medicalHistory = medicalHistoryRepository.findById(userMedicalHistoryDTO.getConditionId());
        if(medicalHistory.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        userMedicalHistory.setAppUserId(appUser.get());
        userMedicalHistory.setConditionId(medicalHistory.get());
        return userMedicalHistoryRepository.save(userMedicalHistory);
    }

    @Override
    public UserMedicalHistory getUserMedicalHistoryById(Integer id) {
        Optional<UserMedicalHistory> userMedicalHistory = userMedicalHistoryRepository.findById(id);
        if(userMedicalHistory.isEmpty()){
            throw new AppException(ErrorCode.USER_MEDICAL_HISTORY_NOT_FOUND);
        }
        return userMedicalHistory.get();
    }

    @Override
    public List<UserMedicalHistory> getAllUserMedicalHistory() {
        return userMedicalHistoryRepository.findAll();
    }

//    @Override
//    public UserMedicalHistory updateUserMedicalHistory(Integer id, UserMedicalHistoryDTO userMedicalHistoryDTO) {
//        UserMedicalHistory userMedicalHistory = getUserMedicalHistoryById(id);
//                .id(userMedicalHistoryDTO.getId())
//                .build();
//        Optional<AppUser> appUser = appUserRepository.findById(userMedicalHistoryDTO.getAppUserId());
//        if(appUser.isEmpty()) {
//            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
//        }
//        Optional<MedicalHistory> medicalHistory = medicalHistoryRepository.findById(userMedicalHistoryDTO.getConditionId());
//        if(medicalHistory.isEmpty()) {
//            throw new AppException(ErrorCode.NOT_FOUND);
//        }
//        userMedicalHistory.setAppUserId(appUser.get());
//        userMedicalHistory.setConditionId(medicalHistory.get());
//        return userMedicalHistoryRepository.save(userMedicalHistory);
//    }

    @Override
    public UserMedicalHistory deleteUserMedicalHistory(Integer id) {
        UserMedicalHistory userMedicalHistory = getUserMedicalHistoryById(id);
        userMedicalHistoryRepository.deleteById(id);
        return userMedicalHistory;
    }
}
