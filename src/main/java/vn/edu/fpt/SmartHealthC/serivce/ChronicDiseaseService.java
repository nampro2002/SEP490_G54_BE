package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.ChronicDisease;

import java.util.List;
import java.util.Optional;

public interface ChronicDiseaseService {
    ChronicDisease createChronicDisease(ChronicDisease chronicDisease);
    Optional<ChronicDisease> getChronicDiseaseById(Integer id);
    List<ChronicDisease> getAllChronicDiseases();
    ChronicDisease updateChronicDisease(ChronicDisease chronicDisease);
    void deleteChronicDisease(Integer id);
}
