package vn.edu.fpt.SmartHealthC.serivce;

import ch.qos.logback.classic.spi.LoggingEventVO;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentByWebUserDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.MedicalAppointmentUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.*;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalAppointment;

import java.text.ParseException;
import java.util.List;

public interface MedicalAppointmentService {
    MedicalAppointmentResponseDTO createMedicalAppointment(MedicalAppointmentDTO medicalAppointmentDTO);
    MedicalAppointment getMedicalAppointmentEntityById(Integer id);
    MedicalAppointmentResponseDTO getMedicalAppointmentById(Integer id);
    ResponsePaging<List<MedicalAppointmentResponseDTO>> getAllMedicalAppointments(Integer pageNo, String search);
    MedicalAppointmentResponseDTO updateMedicalAppointment(Integer id, MedicalAppointmentUpdateDTO medicalAppointmentDTO) throws ParseException;
    MedicalAppointmentResponseDTO deleteMedicalAppointment(Integer id);

    ResponsePaging<List<MedicalAppointmentResponseDTO>> getAllMedicalAppointmentsPending(Integer pageNo, TypeMedicalAppointment diagnosis);

    List<MedicalAppointmentResponseDTO> getMedicalAppointmentByUserIdMobile();
    ResponsePaging<List<MedicalAppointmentResponseDTO>> getMedicalAppointmentByUserId(Integer userId,  Integer pageNo);
    List<MedicalAppointment> getMedicalAppointmentConfirm();

    List<ListPatientResponseDTO> getListPatientByWebUserId();

    MedicalAppointmentResponseDTO createMedicalAppointmentByWebUser(MedicalAppointmentByWebUserDTO medicalAppointmentDTO);
}