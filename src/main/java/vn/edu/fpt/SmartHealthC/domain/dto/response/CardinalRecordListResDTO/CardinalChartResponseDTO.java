package vn.edu.fpt.SmartHealthC.domain.dto.response.CardinalRecordListResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardinalChartResponseDTO {
    private Float hba1cDataToday;
    private Float cholesterolDataToday;
    private List<HBA1CResponseDTO> hba1cList = new ArrayList<>();
    private List<CholesterolResponseDTO>  cholesterolList = new ArrayList<>();
    private List<BloodSugarResponseDTO>  bloodSugarList = new ArrayList<>();
    private Map<String, List<DetailBloodSugarResponseDTO>> detailDataBloodSugar = new HashMap<>();
}
