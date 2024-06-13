package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.dto.request.MentalRuleRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.MentalRuleResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;

import java.util.List;
import java.util.Optional;

public interface MentalRuleService {
    MentalRuleResponseDTO createMentalRule(MentalRuleRequestDTO mentalRule);
    MentalRule getMentalRuleEntityById(Integer id);
    MentalRuleResponseDTO getMentalRuleById(Integer id);
    List<MentalRuleResponseDTO> getAllMentalRules();
    MentalRuleResponseDTO updateMentalRule(Integer id, MentalRuleRequestDTO mentalRule);
    MentalRuleResponseDTO deleteMentalRule(Integer id);
}
