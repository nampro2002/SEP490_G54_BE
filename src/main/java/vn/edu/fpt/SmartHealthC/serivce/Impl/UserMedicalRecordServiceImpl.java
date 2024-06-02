package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalRecord;
import vn.edu.fpt.SmartHealthC.repository.UserMedicalRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.UserMedicalRecordService;

import java.util.List;
import java.util.Optional;

@Service
public class UserMedicalRecordServiceImpl implements UserMedicalRecordService {

    @Autowired
    private UserMedicalRecordRepository userMedicalRecordRepository;

    @Override
    public UserMedicalRecord createUserMedicalRecord(UserMedicalRecord userMedicalRecord) {
        return userMedicalRecordRepository.save(userMedicalRecord);
    }

    @Override
    public Optional<UserMedicalRecord> getUserMedicalRecordById(Integer id) {
        return userMedicalRecordRepository.findById(id);
    }

    @Override
    public List<UserMedicalRecord> getAllUserMedicalRecords() {
        return userMedicalRecordRepository.findAll();
    }

    @Override
    public UserMedicalRecord updateUserMedicalRecord(UserMedicalRecord userMedicalRecord) {
        return userMedicalRecordRepository.save(userMedicalRecord);
    }

    @Override
    public void deleteUserMedicalRecord(Integer id) {
        userMedicalRecordRepository.deleteById(id);
    }
}
