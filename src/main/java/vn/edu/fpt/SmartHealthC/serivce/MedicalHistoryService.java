package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalHistoryRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;

import java.util.List;
import java.util.Optional;

public interface MedicalHistoryService {
    MedicalHistory createMedicalHistory(MedicalHistoryRequestDTO medicalHistory);
    MedicalHistory getMedicalHistoryById(Integer id);
    List<MedicalHistory> getAllMedicalHistory();
    MedicalHistory updateMedicalHistory(MedicalHistoryRequestDTO medicalHistory);
    MedicalHistory deleteMedicalHistory(Integer id);
}
