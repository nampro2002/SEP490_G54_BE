package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;

import java.util.List;
import java.util.Optional;

public interface MedicalAppointmentService {
    MedicalAppointment createMedicalAppointment(MedicalAppointmentDTO medicalAppointmentDTO);
    MedicalAppointment getMedicalAppointmentById(Integer id);
    List<MedicalAppointment> getAllMedicalAppointments();
    MedicalAppointment updateMedicalAppointment(MedicalAppointmentDTO medicalAppointmentDTO);
    MedicalAppointment deleteMedicalAppointment(Integer id);
}