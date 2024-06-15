package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalHistoryRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicalHistoryResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;

import java.util.List;
import java.util.Optional;

public interface MedicalHistoryService {
    MedicalHistoryResDTO createMedicalHistory(MedicalHistoryRequestDTO medicalHistory);
    MedicalHistoryResDTO getMedicalHistoryById(Integer id);
    ResponsePaging<List<MedicalHistoryResDTO>> getAllMedicalHistory(Integer pageNo, String search);
    MedicalHistory getMedicalHistoryEntityById(Integer id);
    MedicalHistoryResDTO updateMedicalHistory(Integer id, MedicalHistoryRequestDTO medicalHistory);
    MedicalHistoryResDTO deleteMedicalHistory(Integer id);

    List<MedicalHistoryResDTO> getAllMedicalHistoryMobile();
}
