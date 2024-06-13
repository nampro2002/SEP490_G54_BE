package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ApiResponse;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicalAppointmentResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;

import java.util.List;
import java.util.Optional;

public interface MedicalAppointmentService {
    MedicalAppointmentResponseDTO createMedicalAppointment(MedicalAppointmentDTO medicalAppointmentDTO);
    MedicalAppointment getMedicalAppointmentEntityById(Integer id);
    MedicalAppointmentResponseDTO getMedicalAppointmentById(Integer id);
    List<MedicalAppointmentResponseDTO> getAllMedicalAppointments();
    MedicalAppointmentResponseDTO updateMedicalAppointment(Integer id, MedicalAppointmentDTO medicalAppointmentDTO);
    MedicalAppointmentResponseDTO deleteMedicalAppointment(Integer id);

    ResponsePaging<List<MedicalAppointmentResponseDTO>> getAllMedicalAppointmentsPending(Integer id, Integer pageNo);
}