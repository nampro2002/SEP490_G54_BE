package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointmentStatus;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicalAppointmentRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicalAppointmentService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalAppointmentServiceImpl implements MedicalAppointmentService {

    @Autowired
    private MedicalAppointmentRepository medicalAppointmentRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public MedicalAppointment createMedicalAppointment(MedicalAppointmentDTO medicalAppointmentDTO) {
        MedicalAppointment medicalAppointment = MedicalAppointment.builder()
                .typeMedicalAppointment(medicalAppointmentDTO.getType())
                .hospital(medicalAppointmentDTO.getLocation())
                .date(medicalAppointmentDTO.getDate())
                .statusMedicalAppointment(TypeMedicalAppointmentStatus.PENDING)
                .build();

        Optional<AppUser> appUser = appUserRepository.findById(medicalAppointmentDTO.getAppUserId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        medicalAppointment.setAppUserId(appUser.get());
        return medicalAppointmentRepository.save(medicalAppointment);
    }

    @Override
    public MedicalAppointment getMedicalAppointmentById(Integer id) {
        Optional<MedicalAppointment> medicalAppointment = medicalAppointmentRepository.findById(id);
        if (medicalAppointment.isEmpty()) {
            throw new AppException(ErrorCode.MEDICAL_APPOINTMENT_NOT_FOUND);
        }
        return medicalAppointment.get();
    }

    @Override
    public List<MedicalAppointment> getAllMedicalAppointments() {
        return medicalAppointmentRepository.findAll();
    }

    @Override
    public MedicalAppointment updateMedicalAppointment(Integer id, MedicalAppointmentDTO medicalAppointmentDTO) {
        MedicalAppointment medicalAppointment = getMedicalAppointmentById(id);
        medicalAppointment.setTypeMedicalAppointment(medicalAppointmentDTO.getType());
        medicalAppointment.setDate(medicalAppointmentDTO.getDate());
        medicalAppointment.setHospital(medicalAppointmentDTO.getLocation());
        return medicalAppointmentRepository.save(medicalAppointment);
    }

    @Override
    public MedicalAppointment deleteMedicalAppointment(Integer id) {
        MedicalAppointment medicalAppointment = getMedicalAppointmentById(id);
        medicalAppointmentRepository.deleteById(id);
        return medicalAppointment;
    }

    @Override
    public ResponsePaging<List<MedicalAppointmentResponseDTO>> getAllMedicalAppointmentsPending(Integer id, Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<MedicalAppointment> pagedResult = medicalAppointmentRepository.findAll(paging);
        List<MedicalAppointment> medicalAppointmentList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            medicalAppointmentList = pagedResult.getContent();
        }
        List<MedicalAppointmentResponseDTO> listResponse =  medicalAppointmentList.stream()
                .filter(record -> (record.getStatusMedicalAppointment().equals(TypeMedicalAppointmentStatus.PENDING) &&
                        record.getAppUserId().getId() == id))
                .map(record -> {
                    MedicalAppointmentResponseDTO dto = new MedicalAppointmentResponseDTO();
                    dto.setId(record.getId());
                    dto.setAppUserName(record.getAppUserId().getName());
                    dto.setDate(record.getDate());
                    dto.setHospital(record.getHospital());
                    dto.setTypeMedicalAppointment(record.getTypeMedicalAppointment());
                    dto.setStatusMedicalAppointment(record.getStatusMedicalAppointment());
                    return dto;
                })
                .toList();
        return ResponsePaging.<List<MedicalAppointmentResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(listResponse)
                .build();
    }


}