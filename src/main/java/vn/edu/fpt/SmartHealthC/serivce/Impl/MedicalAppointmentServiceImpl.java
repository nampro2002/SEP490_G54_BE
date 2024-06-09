package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.MedicalAppointmentRepository;
import vn.edu.fpt.SmartHealthC.serivce.MedicalAppointmentService;
import vn.edu.fpt.SmartHealthC.serivce.StepRecordService;

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
                .type(medicalAppointmentDTO.getType())
                .hospital(medicalAppointmentDTO.getLocation())
                .date(medicalAppointmentDTO.getDate()).build();
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
    public MedicalAppointment updateMedicalAppointment(MedicalAppointmentDTO medicalAppointmentDTO) {
        MedicalAppointment medicalAppointment = MedicalAppointment.builder()
                .id(medicalAppointmentDTO.getId())
                .type(medicalAppointmentDTO.getType())
                .hospital(medicalAppointmentDTO.getLocation())
                .date(medicalAppointmentDTO.getDate()).build();
        Optional<AppUser> appUser = appUserRepository.findById(medicalAppointmentDTO.getAppUserId());
        if (appUser.isEmpty()) {
            throw new AppException(ErrorCode.APP_USER_NOT_FOUND);
        }

        medicalAppointment.setAppUserId(appUser.get());
        return medicalAppointmentRepository.save(medicalAppointment);
    }

    @Override
    public MedicalAppointment deleteMedicalAppointment(Integer id) {
        MedicalAppointment medicalAppointment = getMedicalAppointmentById(id);
        medicalAppointmentRepository.deleteById(id);
        return medicalAppointment;
    }


}