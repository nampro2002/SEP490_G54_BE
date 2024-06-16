package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointmentStatus;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalHistoryRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicalAppointmentResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicalHistoryResDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
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
                .isDeleted(false)
                .build();
        medicalHistory =  medicalHistoryRepository.save(medicalHistory);
        MedicalHistoryResDTO medicalHistoryResDTO = MedicalHistoryResDTO
                .builder()
                .id(medicalHistory.getId())
                .name(medicalHistory.getName())
                .type(medicalHistory.getType())
                .isDeleted(medicalHistory.isDeleted())
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
                .id(medicalHistory.get().getId())
                .name(medicalHistory.get().getName())
                .type(medicalHistory.get().getType())
                .build();
        return medicalHistoryResDTO;
    }

    @Override
    public ResponsePaging<List<MedicalHistoryResDTO>> getAllMedicalHistory(Integer pageNo, String search) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<MedicalHistory> pagedResult = medicalHistoryRepository.findAllNotDeleted(paging, search);
        List<MedicalHistory> medicalHistories= new ArrayList<>();
        if (pagedResult.hasContent()) {
            medicalHistories = pagedResult.getContent();
        }
        List<MedicalHistoryResDTO> medicalHistoryResDTOList = new ArrayList<>();
        for (MedicalHistory medicalHistory : medicalHistories) {
            MedicalHistoryResDTO medicalHistoryResDTO = MedicalHistoryResDTO
                    .builder()
                    .id(medicalHistory.getId())
                    .name(medicalHistory.getName())
                    .type(medicalHistory.getType())
                    .build();
            medicalHistoryResDTOList.add(medicalHistoryResDTO);
        }
        return ResponsePaging.<List<MedicalHistoryResDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(medicalHistoryResDTOList)
                .build();
    }

    @Override
    public MedicalHistoryResDTO updateMedicalHistory(Integer id, MedicalHistoryRequestDTO medicalHistoryRequestDTO) {
        MedicalHistory medicalHistory = getMedicalHistoryEntityById(id);
        medicalHistory.setName(medicalHistoryRequestDTO.getName());
        medicalHistory.setType(medicalHistoryRequestDTO.getType());
        medicalHistory.setDeleted(medicalHistoryRequestDTO.isDeleted());
        medicalHistoryRepository.save(medicalHistory);
        MedicalHistoryResDTO medicalHistoryResDTO = MedicalHistoryResDTO
                .builder()
                .id(medicalHistory.getId())
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
                .id(medicalHistory.getId())
                .name(medicalHistory.getName())
                .type(medicalHistory.getType())
                .build();
        return medicalHistoryResDTO;
    }

    @Override
    public List<MedicalHistoryResDTO> getAllMedicalHistoryMobile() {
        List<MedicalHistory> medicalHistories = medicalHistoryRepository.findAllNotDeleted();

        List<MedicalHistoryResDTO> medicalHistoryResDTOList = new ArrayList<>();
        for (MedicalHistory medicalHistory : medicalHistories) {
            MedicalHistoryResDTO medicalHistoryResDTO = MedicalHistoryResDTO
                    .builder()
                    .id(medicalHistory.getId())
                    .name(medicalHistory.getName())
                    .type(medicalHistory.getType())
                    .build();
            medicalHistoryResDTOList.add(medicalHistoryResDTO);
        }
        return medicalHistoryResDTOList;
    }
}
