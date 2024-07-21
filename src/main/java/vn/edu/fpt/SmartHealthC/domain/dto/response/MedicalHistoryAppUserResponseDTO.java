package vn.edu.fpt.SmartHealthC.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.entity.MedicalHistory;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryAppUserResponseDTO {

    private Integer id;
    private String name;

}
