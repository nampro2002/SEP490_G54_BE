package vn.edu.fpt.SmartHealthC.domain.dto.request;


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
public class MedicalAppointmentDTO {
    @NotNull(message = "missing appUserId")
    private int appUserId;
    private String location;
    @NotNull(message = "missing type")
    private TypeMedicalAppointment type;

    private Date date;


}
