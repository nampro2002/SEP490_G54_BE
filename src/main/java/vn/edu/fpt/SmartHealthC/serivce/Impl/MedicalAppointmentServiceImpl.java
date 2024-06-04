package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.SmartHealthC.domain.dto.MedicalAppointmentDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.StepRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.entity.StepRecord;
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
    public MedicalAppointment createMedicalAppointment(MedicalAppointmentDTO medicalAppointmentDTO)
    {
        MedicalAppointment medicalAppointment =  MedicalAppointment.builder()
                .type(medicalAppointmentDTO.getType())
                .location(medicalAppointmentDTO.getLocation())
                .date(medicalAppointmentDTO.getDate()).build();
        AppUser appUser = appUserRepository.findById(medicalAppointmentDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        medicalAppointment.setAppUserId(appUser);
        return  medicalAppointmentRepository.save(medicalAppointment);
    }

    @Override
    public Optional<MedicalAppointment> getMedicalAppointmentById(Integer id) {
        return medicalAppointmentRepository.findById(id);
    }

    @Override
    public List<MedicalAppointment> getAllMedicalAppointments() {
        return medicalAppointmentRepository.findAll();
    }

    @Override
    public MedicalAppointment updateMedicalAppointment(MedicalAppointmentDTO medicalAppointmentDTO) {
        MedicalAppointment medicalAppointment =  MedicalAppointment.builder()
                .Id(medicalAppointmentDTO.getId())
                .type(medicalAppointmentDTO.getType())
                .location(medicalAppointmentDTO.getLocation())
                .date(medicalAppointmentDTO.getDate()).build();
        AppUser appUser = appUserRepository.findById(medicalAppointmentDTO.getAppUserId())
                .orElseThrow(() -> new IllegalArgumentException("AppUser not found"));

        medicalAppointment.setAppUserId(appUser);
        return  medicalAppointmentRepository.save(medicalAppointment);
    }

    @Override
    public void deleteMedicalAppointment(Integer id) {
        medicalAppointmentRepository.deleteById(id);
    }


}