package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalHistoryRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.MedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicalHistoryService;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Override
    public MedicalHistory createMedicalHistory(MedicalHistoryRequestDTO medicalHistoryRequestDTO) {
        MedicalHistory medicalHistory = MedicalHistory
                .builder()
                .name(medicalHistoryRequestDTO.getName())
                .type(medicalHistoryRequestDTO.getType())
                .build();
        return medicalHistoryRepository.save(medicalHistory);
    }

    @Override
    public MedicalHistory getMedicalHistoryById(Integer id) {
        Optional<MedicalHistory> medicalHistory = medicalHistoryRepository.findById(id);
        if (medicalHistory.isEmpty()) {
            throw new AppException(ErrorCode.MEDICAL_HISTORY_NOT_FOUND);
        }
        return medicalHistory.get();
    }

    @Override
    public List<MedicalHistory> getAllMedicalHistory() {
        return medicalHistoryRepository.findAll();
    }

    @Override
    public MedicalHistory updateMedicalHistory(MedicalHistoryRequestDTO medicalHistoryRequestDTO) {
        MedicalHistory medicalHistory = getMedicalHistoryById(medicalHistoryRequestDTO.getId());
        medicalHistory.setName(medicalHistoryRequestDTO.getName());
        medicalHistory.setType(medicalHistoryRequestDTO.getType());
        return medicalHistoryRepository.save(medicalHistory);
    }

    @Override
    public MedicalHistory deleteMedicalHistory(Integer id) {
        MedicalHistory medicalHistory = getMedicalHistoryById(id);
        medicalHistoryRepository.deleteById(id);
        return medicalHistory;
    }
}
