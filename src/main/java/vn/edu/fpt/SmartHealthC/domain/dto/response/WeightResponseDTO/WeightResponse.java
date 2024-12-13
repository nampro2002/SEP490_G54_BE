package vn.edu.fpt.SmartHealthC.domain.dto.response.WeightResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeightResponse {

    private Float value;
    private Date date;



}
