package vn.edu.fpt.SmartHealthC.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDTO {
    private List<QuestionResponseDTO> questionResponseDTOS;
    private List<MedicalAppointmentResponseDTO> medicalAppointmentResponseDTOList;
    private List<AppUserResponseDTO> appUserResponseDTOList;
    private List<AvailableMSResponseDTO> availableMSResponseDTOList;
}
