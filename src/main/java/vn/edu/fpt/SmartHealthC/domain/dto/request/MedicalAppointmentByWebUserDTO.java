package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicalAppointment;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalAppointmentByWebUserDTO {
    @NotNull(message = "missing patientId")
    private Integer patientId;
    private String location;
    @NotNull(message = "missing type")
    private TypeMedicalAppointment type;
    @NotBlank(message = "missing note")
    private String note;
    private Date date;


}
