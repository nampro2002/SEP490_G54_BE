package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalHistoryRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicalHistoryResDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.MedicalHistoryRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicalHistoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Override
    public MedicalHistoryResDTO createMedicalHistory(MedicalHistoryRequestDTO medicalHistoryRequestDTO) {
        MedicalHistory medicalHistory = MedicalHistory
                .builder()
                .name(medicalHistoryRequestDTO.getName())
                .type(medicalHistoryRequestDTO.getType())
                .build();
        medicalHistory =  medicalHistoryRepository.save(medicalHistory);
        MedicalHistoryResDTO medicalHistoryResDTO = MedicalHistoryResDTO
                .builder()
                .name(medicalHistory.getName())
                .type(medicalHistory.getType())
                .build();
        return medicalHistoryResDTO;
    }

    @Override
    public MedicalHistory getMedicalHistoryEntityById(Integer id) {
        Optional<MedicalHistory> medicalHistory = medicalHistoryRepository.findById(id);
        if (medicalHistory.isEmpty()) {
            throw new AppException(ErrorCode.MEDICAL_HISTORY_NOT_FOUND);
        }
        return medicalHistory.get();
    }

    @Override
    public MedicalHistoryResDTO getMedicalHistoryById(Integer id) {
        Optional<MedicalHistory> medicalHistory = medicalHistoryRepository.findById(id);
        if (medicalHistory.isEmpty()) {
            throw new AppException(ErrorCode.MEDICAL_HISTORY_NOT_FOUND);
        }
        MedicalHistoryResDTO medicalHistoryResDTO = MedicalHistoryResDTO
                .builder()
                .name(medicalHistory.get().getName())
                .type(medicalHistory.get().getType())
                .build();
        return medicalHistoryResDTO;
    }

    @Override
    public List<MedicalHistoryResDTO> getAllMedicalHistory() {
        List<MedicalHistory> medicalHistories = medicalHistoryRepository.findAll();
        List<MedicalHistoryResDTO> medicalHistoryResDTOList = new ArrayList<>();
        for (MedicalHistory medicalHistory : medicalHistories) {
            MedicalHistoryResDTO medicalHistoryResDTO = MedicalHistoryResDTO
                    .builder()
                    .name(medicalHistory.getName())
                    .type(medicalHistory.getType())
                    .build();
            medicalHistoryResDTOList.add(medicalHistoryResDTO);
        }
        return medicalHistoryResDTOList;
    }

    @Override
    public MedicalHistoryResDTO updateMedicalHistory(Integer id, MedicalHistoryRequestDTO medicalHistoryRequestDTO) {
        MedicalHistory medicalHistory = getMedicalHistoryEntityById(id);
        medicalHistory.setName(medicalHistoryRequestDTO.getName());
        medicalHistory.setType(medicalHistoryRequestDTO.getType());
        medicalHistoryRepository.save(medicalHistory);
        MedicalHistoryResDTO medicalHistoryResDTO = MedicalHistoryResDTO
                .builder()
                .name(medicalHistory.getName())
                .type(medicalHistory.getType())
                .build();
        return medicalHistoryResDTO;
    }

    @Override
    public MedicalHistoryResDTO deleteMedicalHistory(Integer id) {
        MedicalHistory medicalHistory = getMedicalHistoryEntityById(id);
        medicalHistoryRepository.deleteById(id);
        MedicalHistoryResDTO medicalHistoryResDTO = MedicalHistoryResDTO
                .builder()
                .name(medicalHistory.getName())
                .type(medicalHistory.getType())
                .build();
        return medicalHistoryResDTO;
    }
}
