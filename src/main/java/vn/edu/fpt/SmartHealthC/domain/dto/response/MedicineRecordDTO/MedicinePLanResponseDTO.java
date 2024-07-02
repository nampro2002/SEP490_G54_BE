package vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicinePLanResponseDTO {
    private Integer medicineTypeId;
    private String medicineTitle;
    private String time;
    private List<String> weekday = new ArrayList<String>();
}
