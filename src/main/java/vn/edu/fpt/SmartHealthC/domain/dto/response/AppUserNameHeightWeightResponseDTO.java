package vn.edu.fpt.SmartHealthC.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserNameHeightWeightResponseDTO {


    private String name;
    private Float height;
    private Float weight;
    private  MedicalHistoryAppUserResponseDTO medicalUser;

}
