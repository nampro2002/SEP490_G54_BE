package vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineResponseChartDTO {

    private int doneToday;
    private int totalToday;
    private List<MedicineResponse> medicineResponseList = new ArrayList<>();


}
