package vn.edu.fpt.SmartHealthC.serivce;

import ch.qos.logback.classic.spi.LoggingEventVO;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DashboardCountAdmin;
import vn.edu.fpt.SmartHealthC.domain.dto.response.DashboardCountMs;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MedicalAppointmentResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;

import java.util.List;

public interface MedicalAppointmentService {
    MedicalAppointmentResponseDTO createMedicalAppointment(MedicalAppointmentDTO medicalAppointmentDTO);
    MedicalAppointment getMedicalAppointmentEntityById(Integer id);
    MedicalAppointmentResponseDTO getMedicalAppointmentById(Integer id);
    ResponsePaging<List<MedicalAppointmentResponseDTO>> getAllMedicalAppointments(Integer pageNo, String search);
    MedicalAppointmentResponseDTO updateMedicalAppointment(Integer id, MedicalAppointmentDTO medicalAppointmentDTO);
    MedicalAppointmentResponseDTO deleteMedicalAppointment(Integer id);

    ResponsePaging<List<MedicalAppointmentResponseDTO>> getAllMedicalAppointmentsPending(Integer id, Integer pageNo, TypeMedicalAppointment diagnosis);

}