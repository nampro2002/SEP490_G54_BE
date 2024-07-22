package vn.edu.fpt.SmartHealthC.domain.dto.response.MedicineRecordDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicinePlanPerDayResponse {
    private int id;
    private int medicineId;
    private String medicineName;
    private Date date;

}
