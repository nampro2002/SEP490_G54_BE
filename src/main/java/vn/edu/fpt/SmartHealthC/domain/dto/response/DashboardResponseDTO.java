package vn.edu.fpt.SmartHealthC.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeUserQuestion;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDTO {
    private List<QuestionResponseDTO> questionResponseDTOS = new ArrayList<>();
    private List<MedicalAppointmentResponseDTO> medicalAppointmentResponseDTOList = new ArrayList<>();
    private List<AppUserResponseDTO> appUserResponseDTOList = new ArrayList<>();
    private List<AvailableMSResponseDTO> availableMSResponseDTOList = new ArrayList<>();
}
