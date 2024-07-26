package vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicinePLanResponseDTO {
    private Integer medicineTypeId;
    private String medicineTitle;
    private String medicineTitleEn;
    private String time;
    private List<String> weekday = new ArrayList<String>();
    private List<Date> weekTime = new ArrayList<Date>();
    private List<Integer> indexDay = new ArrayList<Integer>();
}
