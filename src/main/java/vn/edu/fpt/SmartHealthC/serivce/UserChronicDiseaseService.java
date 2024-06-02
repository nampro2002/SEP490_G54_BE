package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.UserChronicDisease;

import java.util.List;
import java.util.Optional;

public interface UserChronicDiseaseService {
    UserChronicDisease createUserChronicDisease(UserChronicDisease userChronicDisease);
    Optional<UserChronicDisease> getUserChronicDiseaseById(Integer id);
    List<UserChronicDisease> getAllUserChronicDiseases();
    UserChronicDisease updateUserChronicDisease(UserChronicDisease userChronicDisease);
    void deleteUserChronicDisease(Integer id);
}
