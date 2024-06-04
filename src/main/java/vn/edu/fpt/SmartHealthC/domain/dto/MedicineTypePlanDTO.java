package vn.edu.fpt.SmartHealthC.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineTypePlanDTO {

    private int id;

    private int medicinePlanId;

    private int medicineTypeId;

}
