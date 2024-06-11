package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.UserMedicalHistoryDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalHistory;

import java.util.List;
import java.util.Optional;

public interface UserMedicalHistoryService {
    UserMedicalHistory createUserMedicalHistory(UserMedicalHistoryDTO userMedicalHistoryDTO);
    UserMedicalHistory getUserMedicalHistoryById(Integer id);
    List<UserMedicalHistory> getAllUserMedicalHistory();
//    UserMedicalHistory updateUserMedicalHistory(Integer id, UserMedicalHistoryDTO userMedicalHistoryDTO);
    UserMedicalHistory deleteUserMedicalHistory(Integer id);
}
