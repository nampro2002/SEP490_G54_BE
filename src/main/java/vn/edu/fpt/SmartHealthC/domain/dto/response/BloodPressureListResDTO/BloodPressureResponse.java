package vn.edu.fpt.SmartHealthC.domain.dto.response.BloodPressureListResDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BloodPressureResponse {

    //Tâm thu
    private Float systole;
    //Tâm trương
    private Float diastole;

    private Date date;



}
