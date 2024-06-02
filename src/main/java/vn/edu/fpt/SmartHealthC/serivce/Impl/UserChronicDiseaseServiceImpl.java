package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.UserChronicDisease;
import vn.edu.fpt.SmartHealthC.repository.UserChronicDiseaseRepository;
import vn.edu.fpt.SmartHealthC.serivce.UserChronicDiseaseService;

import java.util.List;
import java.util.Optional;

@Service
public class UserChronicDiseaseServiceImpl  implements UserChronicDiseaseService {

    @Autowired
    private UserChronicDiseaseRepository userChronicDiseaseRepository;

    @Override
    public UserChronicDisease createUserChronicDisease(UserChronicDisease userChronicDisease) {
        return userChronicDiseaseRepository.save(userChronicDisease);
    }

    @Override
    public Optional<UserChronicDisease> getUserChronicDiseaseById(Integer id) {
        return userChronicDiseaseRepository.findById(id);
    }

    @Override
    public List<UserChronicDisease> getAllUserChronicDiseases() {
        return userChronicDiseaseRepository.findAll();
    }

    @Override
    public UserChronicDisease updateUserChronicDisease(UserChronicDisease userChronicDisease) {
        return userChronicDiseaseRepository.save(userChronicDisease);
    }

    @Override
    public void deleteUserChronicDisease(Integer id) {
        userChronicDiseaseRepository.deleteById(id);
    }
}
