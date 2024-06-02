package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.ChronicDisease;
import vn.edu.fpt.SmartHealthC.repository.ChronicDiseaseRepository;
import vn.edu.fpt.SmartHealthC.serivce.ChronicDiseaseService;

import java.util.List;
import java.util.Optional;

@Service
public class ChronicDiseaseServiceImpl implements ChronicDiseaseService {

    @Autowired
    private ChronicDiseaseRepository chronicDiseaseRepository;

    @Override
    public ChronicDisease createChronicDisease(ChronicDisease chronicDisease) {
        return chronicDiseaseRepository.save(chronicDisease);
    }

    @Override
    public Optional<ChronicDisease> getChronicDiseaseById(Integer id) {
        return chronicDiseaseRepository.findById(id);
    }

    @Override
    public List<ChronicDisease> getAllChronicDiseases() {
        return chronicDiseaseRepository.findAll();
    }

    @Override
    public ChronicDisease updateChronicDisease(ChronicDisease chronicDisease) {
        return chronicDiseaseRepository.save(chronicDisease);
    }

    @Override
    public void deleteChronicDisease(Integer id) {
        chronicDiseaseRepository.deleteById(id);
    }
}
