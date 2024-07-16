package vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO.ScreenTotal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SatScreenTotalResponseDTO {


    private float sat_sf_c_total= 0f;
    private float sat_sf_p_total= 0f;
    private float sat_sf_i_total= 0f;


}
