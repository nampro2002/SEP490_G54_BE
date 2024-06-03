package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;
import vn.edu.fpt.SmartHealthC.repository.MedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicalHistoryService;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Override
    public MedicalHistory createMedicalHistory(MedicalHistory medicalHistory) {
        return medicalHistoryRepository.save(medicalHistory);
    }

    @Override
    public Optional<MedicalHistory> getMedicalHistoryById(Integer id) {
        return medicalHistoryRepository.findById(id);
    }

    @Override
    public List<MedicalHistory> getAllMedicalHistories() {
        return medicalHistoryRepository.findAll();
    }

    @Override
    public MedicalHistory updateMedicalHistory(MedicalHistory medicalHistory) {
        return medicalHistoryRepository.save(medicalHistory);
    }

    @Override
    public void deleteMedicalHistory(Integer id) {
        medicalHistoryRepository.deleteById(id);
    }
}
