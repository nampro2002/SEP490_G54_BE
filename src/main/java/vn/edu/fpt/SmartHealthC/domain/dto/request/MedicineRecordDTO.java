package vn.edu.fpt.SmartHealthC.domain.dto.request;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.TypeMedicine;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineRecordDTO {

    private int id;

    private int appUserId;

    private Date weekStart;

    @Enumerated(EnumType.STRING)
    private TypeMedicine type;

    private Float hour;

    private Date date;

    private Boolean status;

}
