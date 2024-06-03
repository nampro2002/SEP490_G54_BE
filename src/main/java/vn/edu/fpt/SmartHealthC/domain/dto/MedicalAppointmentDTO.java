package vn.edu.fpt.SmartHealthC.domain.dto;


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

    private int id;

    private int appUserId;

    private String  location;

    private TypeMedicalAppointment type;

    private Date date;


}
