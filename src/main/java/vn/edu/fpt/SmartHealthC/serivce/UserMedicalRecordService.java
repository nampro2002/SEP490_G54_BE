package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalRecord;

import java.util.List;
import java.util.Optional;

public interface UserMedicalRecordService {
    UserMedicalRecord createUserMedicalRecord(UserMedicalRecord userMedicalRecord);
    Optional<UserMedicalRecord> getUserMedicalRecordById(Integer id);
    List<UserMedicalRecord> getAllUserMedicalRecords();
    UserMedicalRecord updateUserMedicalRecord(UserMedicalRecord userMedicalRecord);
    void deleteUserMedicalRecord(Integer id);
}
