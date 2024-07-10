package vn.edu.fpt.SmartHealthC.domain.dto.response.MonthlyQuestionDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.SmartHealthC.domain.Enum.MonthlyRecordType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SatResponseDTO {

    private float total = 0f;
    private float sat_sf_c_activityPoint= 0f;
    private float sat_sf_c_positivityPoint= 0f;
    private float sat_sf_c_supportPoint= 0f;
    private float sat_sf_c_experiencePoint= 0f;

    private float sat_sf_p_lifeValue= 0f;
    private float sat_sf_p_targetAndAction= 0f;
    private float sat_sf_p_decision= 0f;
    private float sat_sf_p_buildPlan= 0f;
    private float sat_sf_p_healthyEnvironment= 0f;

    private float sat_sf_i_e_activityPoint= 0f;
    private float sat_sf_i_e_activityStressPoint= 0f;
    private float sat_sf_i_e_activitySubstantialPoint= 0f;
    private float sat_sf_i_e_energy= 0f;
    private float sat_sf_i_e_motivation= 0f;
    private float sat_sf_i_e_planCheck= 0f;

    private float sat_sf_c_total= 0f;
    private float sat_sf_p_total= 0f;
    private float sat_sf_i_total= 0f;


}
