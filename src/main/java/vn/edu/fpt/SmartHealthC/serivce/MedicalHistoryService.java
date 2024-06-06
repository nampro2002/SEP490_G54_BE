package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;

import java.util.List;
import java.util.Optional;

public interface MedicalHistoryService {
    MedicalHistory createMedicalHistory(MedicalHistory medicalHistory);
    Optional<MedicalHistory> getMedicalHistoryById(Integer id);
    List<MedicalHistory> getAllMedicalHistory();
    MedicalHistory updateMedicalHistory(MedicalHistory medicalHistory);
    void deleteMedicalHistory(Integer id);
}
