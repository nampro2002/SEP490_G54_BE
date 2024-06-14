package vn.edu.fpt.SmartHealthC.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistoryResponseDTO {
    private String title;
    private List<MedicalRecord> data;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
 class MedicalRecord{
    private int id;
    private String name;
}