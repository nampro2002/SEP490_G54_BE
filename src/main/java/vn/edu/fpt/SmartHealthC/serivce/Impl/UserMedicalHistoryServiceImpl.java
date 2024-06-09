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
    public Optional<UserMedicalHistory> getUserMedicalHistoryById(Integer id) {
        return userMedicalHistoryRepository.findById(id);
    }

    @Override
    public List<UserMedicalHistory> getAllUserMedicalHistory() {
        return userMedicalHistoryRepository.findAll();
    }

    @Override
    public UserMedicalHistory updateUserMedicalHistory(UserMedicalHistoryDTO userMedicalHistoryDTO) {
        UserMedicalHistory userMedicalHistory =  UserMedicalHistory.builder()
                .id(userMedicalHistoryDTO.getId())
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
    public void deleteUserMedicalHistory(Integer id) {
        userMedicalHistoryRepository.deleteById(id);
    }
}
