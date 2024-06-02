package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;

import java.util.List;
import java.util.Optional;

public interface MentalRuleService {
    MentalRule createMentalRule(MentalRule mentalRule);
    Optional<MentalRule> getMentalRuleById(Integer id);
    List<MentalRule> getAllMentalRules();
    MentalRule updateMentalRule(MentalRule mentalRule);
    void deleteMentalRule(Integer id);
}
