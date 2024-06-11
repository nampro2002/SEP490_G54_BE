package vn.edu.fpt.SmartHealthC.domain.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointment;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointmentStatus;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalAppointmentResponseDTO {
    private Integer id;

    private String appUserName;

    private Date date;

    private String hospital;

    private TypeMedicalAppointment typeMedicalAppointment;

    private TypeMedicalAppointmentStatus statusMedicalAppointment;
}
