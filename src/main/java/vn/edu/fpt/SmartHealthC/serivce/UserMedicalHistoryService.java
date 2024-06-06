package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalHistory;

import java.util.List;
import java.util.Optional;

public interface UserMedicalHistoryService {
    UserMedicalHistory createUserMedicalHistory(UserMedicalHistory userMedicalHistory);
    Optional<UserMedicalHistory> getUserMedicalHistoryById(Integer id);
    List<UserMedicalHistory> getAllUserMedicalHistory();
    UserMedicalHistory updateUserMedicalHistory(UserMedicalHistory userMedicalHistory);
    void deleteUserMedicalHistory(Integer id);
}
