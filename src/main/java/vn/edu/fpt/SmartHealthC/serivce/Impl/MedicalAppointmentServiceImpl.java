package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointmentStatus;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.domain.entity.*;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicalAppointmentRepository;
import vn.edu.fpt.SmartHealthC.serivce.AccountService;
import vn.edu.fpt.SmartHealthC.serivce.MedicalAppointmentService;
import vn.edu.fpt.SmartHealthC.serivce.WebUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalAppointmentServiceImpl implements MedicalAppointmentService {

    @Autowired
    private MedicalAppointmentRepository medicalAppointmentRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private WebUserService webUserService;

    @Override
    public MedicalAppointmentResponseDTO createMedicalAppointment(MedicalAppointmentDTO medicalAppointmentDTO) {
        MedicalAppointment medicalAppointment = MedicalAppointment.builder()
                .typeMedicalAppointment(medicalAppointmentDTO.getType())
                .hospital(medicalAppointmentDTO.getLocation())
                .date(medicalAppointmentDTO.getDate())
                .statusMedicalAppointment(TypeMedicalAppointmentStatus.PENDING)
                .note(medicalAppointmentDTO.getNote())
                .build();

        Optional<AppUser> appUser = appUserRepository.findById(medicalAppointmentDTO.getAppUserId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        medicalAppointment.setAppUserId(appUser.get());
        medicalAppointment = medicalAppointmentRepository.save(medicalAppointment);
        MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                .id(medicalAppointment.getId())
                .appUserName(medicalAppointment.getAppUserId().getName())
                .date(medicalAppointment.getDate())
                .hospital(medicalAppointment.getHospital())
                .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                .statusMedicalAppointment(medicalAppointment.getStatusMedicalAppointment())
                .note(medicalAppointment.getNote())
                .build();
        return medicalAppointmentResponseDTO;
    }

    @Override
    public MedicalAppointment getMedicalAppointmentEntityById(Integer id) {
        Optional<MedicalAppointment> medicalAppointment = medicalAppointmentRepository.findById(id);
        if (medicalAppointment.isEmpty()) {
            throw new AppException(ErrorCode.MEDICAL_APPOINTMENT_NOT_FOUND);
        }
        return medicalAppointment.get();
    }

    @Override
    public MedicalAppointmentResponseDTO getMedicalAppointmentById(Integer id) {
        Optional<MedicalAppointment> medicalAppointment = medicalAppointmentRepository.findById(id);
        if (medicalAppointment.isEmpty()) {
            throw new AppException(ErrorCode.MEDICAL_APPOINTMENT_NOT_FOUND);
        }
        MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                .id(medicalAppointment.get().getId())
                .appUserName(medicalAppointment.get().getAppUserId().getName())
                .date(medicalAppointment.get().getDate())
                .hospital(medicalAppointment.get().getHospital())
                .typeMedicalAppointment(medicalAppointment.get().getTypeMedicalAppointment())
                .statusMedicalAppointment(medicalAppointment.get().getStatusMedicalAppointment())
                .note(medicalAppointment.get().getNote())
                .build();
        return medicalAppointmentResponseDTO;

    }

    @Override
    public ResponsePaging<List<MedicalAppointmentResponseDTO>> getAllMedicalAppointments(Integer pageNo, String search) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        WebUser webUser = webUserService.getWebUserByEmail(email);
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<MedicalAppointment> pagedResult = medicalAppointmentRepository.findAllByWebUserId(webUser.getId(), paging, search.toLowerCase());
        List<MedicalAppointment> medicalAppointmentList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            medicalAppointmentList = pagedResult.getContent();
        }
        List<MedicalAppointmentResponseDTO> responseDTOList = new ArrayList<>();
        for (MedicalAppointment medicalAppointment : medicalAppointmentList) {
            MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                    .id(medicalAppointment.getId())
                    .appUserName(medicalAppointment.getAppUserId().getName())
                    .date(medicalAppointment.getDate())
                    .hospital(medicalAppointment.getHospital())
                    .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                    .statusMedicalAppointment(medicalAppointment.getStatusMedicalAppointment())
                    .note(medicalAppointment.getNote())
                    .build();
            responseDTOList.add(medicalAppointmentResponseDTO);
        }
        return ResponsePaging.<List<MedicalAppointmentResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(responseDTOList)
                .build();
    }

    @Override
    public MedicalAppointmentResponseDTO updateMedicalAppointment(Integer id, MedicalAppointmentDTO medicalAppointmentDTO) {
        MedicalAppointment medicalAppointment = getMedicalAppointmentEntityById(id);
        medicalAppointment.setTypeMedicalAppointment(medicalAppointmentDTO.getType());
        medicalAppointment.setDate(medicalAppointmentDTO.getDate());
        medicalAppointment.setHospital(medicalAppointmentDTO.getLocation());
        medicalAppointment.setNote(medicalAppointmentDTO.getNote());
        medicalAppointment = medicalAppointmentRepository.save(medicalAppointment);
        MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                .id(medicalAppointment.getId())
                .appUserName(medicalAppointment.getAppUserId().getName())
                .date(medicalAppointment.getDate())
                .hospital(medicalAppointment.getHospital())
                .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                .statusMedicalAppointment(medicalAppointment.getStatusMedicalAppointment())
                .note(medicalAppointment.getNote())
                .build();
        return medicalAppointmentResponseDTO;
    }

    @Override
    public MedicalAppointmentResponseDTO deleteMedicalAppointment(Integer id) {
        MedicalAppointment medicalAppointment = getMedicalAppointmentEntityById(id);
        medicalAppointmentRepository.deleteById(id);
        MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                .id(medicalAppointment.getId())
                .appUserName(medicalAppointment.getAppUserId().getName())
                .date(medicalAppointment.getDate())
                .hospital(medicalAppointment.getHospital())
                .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                .statusMedicalAppointment(medicalAppointment.getStatusMedicalAppointment())
                .note(medicalAppointment.getNote())
                .build();
        return medicalAppointmentResponseDTO;
    }

    @Override
    public ResponsePaging<List<MedicalAppointmentResponseDTO>> getAllMedicalAppointmentsPending(Integer pageNo, TypeMedicalAppointment type) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        WebUser webUser = webUserService.getWebUserByEmail(email);
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<MedicalAppointment> pagedResult = medicalAppointmentRepository.findAllPendingByUserIdAndType(TypeMedicalAppointmentStatus.PENDING, webUser.getId(), type, paging);
        List<MedicalAppointment> medicalAppointmentList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            medicalAppointmentList = pagedResult.getContent();
        }
        List<MedicalAppointmentResponseDTO> listResponse = medicalAppointmentList.stream()
//                .filter(record -> (record.getStatusMedicalAppointment().equals(TypeMedicalAppointmentStatus.PENDING) &&
//                        record.getAppUserId().getId() == id))
                .map(record -> {
                    MedicalAppointmentResponseDTO dto = new MedicalAppointmentResponseDTO();
                    dto.setId(record.getId());
                    dto.setAppUserName(record.getAppUserId().getName());
                    dto.setDate(record.getDate());
                    dto.setHospital(record.getHospital());
                    dto.setTypeMedicalAppointment(record.getTypeMedicalAppointment());
                    dto.setStatusMedicalAppointment(record.getStatusMedicalAppointment());
                    dto.setNote(record.getNote());
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


    @Override
    public List<MedicalAppointmentResponseDTO> getMedicalAppointmentByUserIdMobile(Integer userId) {
        List<MedicalAppointment> medicalAppointmentList  = medicalAppointmentRepository.findAllByUserIdAndType(TypeMedicalAppointmentStatus.DONE, userId);
        List<MedicalAppointmentResponseDTO> responseDTOList = new ArrayList<>();
        for (MedicalAppointment medicalAppointment : medicalAppointmentList) {
            MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                    .id(medicalAppointment.getId())
                    .appUserName(medicalAppointment.getAppUserId().getName())
                    .date(medicalAppointment.getDate())
                    .hospital(medicalAppointment.getHospital())
                    .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                    .statusMedicalAppointment(medicalAppointment.getStatusMedicalAppointment())
                    .note(medicalAppointment.getNote())
                    .build();
            responseDTOList.add(medicalAppointmentResponseDTO);
        }
        return responseDTOList;
    }
    @Override
    public ResponsePaging<List<MedicalAppointmentResponseDTO>> getMedicalAppointmentByUserId(Integer userId,  Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, 5, Sort.by("id"));
        Page<MedicalAppointment> pagedResult = medicalAppointmentRepository.findAllByAppUserId(userId, paging);
        List<MedicalAppointment> medicalAppointmentList = new ArrayList<>();
        if (pagedResult.hasContent()) {
            medicalAppointmentList = pagedResult.getContent();
        }
        List<MedicalAppointmentResponseDTO> responseDTOList = new ArrayList<>();
        for (MedicalAppointment medicalAppointment : medicalAppointmentList) {
            MedicalAppointmentResponseDTO medicalAppointmentResponseDTO = MedicalAppointmentResponseDTO.builder()
                    .id(medicalAppointment.getId())
                    .appUserName(medicalAppointment.getAppUserId().getName())
                    .date(medicalAppointment.getDate())
                    .hospital(medicalAppointment.getHospital())
                    .typeMedicalAppointment(medicalAppointment.getTypeMedicalAppointment())
                    .statusMedicalAppointment(medicalAppointment.getStatusMedicalAppointment())
                    .note(medicalAppointment.getNote())
                    .build();
            responseDTOList.add(medicalAppointmentResponseDTO);
        }
        return ResponsePaging.<List<MedicalAppointmentResponseDTO>>builder()
                .totalPages(pagedResult.getTotalPages())
                .currentPage(pageNo + 1)
                .totalItems((int) pagedResult.getTotalElements())
                .dataResponse(responseDTOList)
                .build();
    }



}