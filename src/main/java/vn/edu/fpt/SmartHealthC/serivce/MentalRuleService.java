package vn.edu.fpt.SmartHealthC.serivce;

import vn.edu.fpt.SmartHealthC.domain.entity.MentalRule;

import java.util.List;
import java.util.Optional;

public interface MentalRuleService {
    MentalRule createMentalRule(MentalRule mentalRule);
    MentalRule getMentalRuleById(Integer id);
    List<MentalRule> getAllMentalRules();
    MentalRule updateMentalRule(Integer id,MentalRule mentalRule);
    MentalRule deleteMentalRule(Integer id);
}
