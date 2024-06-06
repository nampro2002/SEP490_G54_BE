package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.UserMedicalHistory;
import vn.edu.fpt.SmartHealthC.repository.UserMedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.serivce.UserMedicalHistoryService;

import java.util.List;
import java.util.Optional;

@Service
public class UserMedicalHistoryServiceImpl implements UserMedicalHistoryService {

    @Autowired
    private UserMedicalHistoryRepository userMedicalHistoryRepository;

    @Override
    public UserMedicalHistory createUserMedicalHistory(UserMedicalHistory userMedicalHistory) {
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
    public UserMedicalHistory updateUserMedicalHistory(UserMedicalHistory userMedicalHistory) {
        return userMedicalHistoryRepository.save(userMedicalHistory);
    }

    @Override
    public void deleteUserMedicalHistory(Integer id) {
        userMedicalHistoryRepository.deleteById(id);
    }
}
