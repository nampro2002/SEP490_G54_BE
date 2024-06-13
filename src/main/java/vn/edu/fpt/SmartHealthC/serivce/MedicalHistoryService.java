package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalHistoryRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicalHistoryResDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;

import java.util.List;
import java.util.Optional;

public interface MedicalHistoryService {
    MedicalHistoryResDTO createMedicalHistory(MedicalHistoryRequestDTO medicalHistory);
    MedicalHistoryResDTO getMedicalHistoryById(Integer id);
    List<MedicalHistoryResDTO> getAllMedicalHistory();
    MedicalHistory getMedicalHistoryEntityById(Integer id);
    MedicalHistoryResDTO updateMedicalHistory(Integer id, MedicalHistoryRequestDTO medicalHistory);
    MedicalHistoryResDTO deleteMedicalHistory(Integer id);
}
